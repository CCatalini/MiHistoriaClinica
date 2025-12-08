package com.example.MiHistoriaClinica.config;

import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.Turnos;
import com.example.MiHistoriaClinica.persistence.repository.MedicRepository;
import com.example.MiHistoriaClinica.persistence.repository.TurnosRepository;
import com.example.MiHistoriaClinica.util.constant.MedicalCenterE;
import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Inicializa la base de datos con turnos disponibles para todos los médicos.
 * Genera 3 meses de turnos con horarios variados según especialidad y médico.
 */
// @Component  // DESHABILITADO - Usar data-turnos.sql manualmente
@Order(2)
public class MedicScheduleDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MedicScheduleDataInitializer.class);

    private final TurnosRepository turnosRepository;
    private final MedicRepository medicRepository;
    
    // Centros médicos disponibles
    private static final MedicalCenterE[] CENTERS = MedicalCenterE.values();

    public MedicScheduleDataInitializer(TurnosRepository turnosRepository, MedicRepository medicRepository) {
        this.turnosRepository = turnosRepository;
        this.medicRepository = medicRepository;
    }

    @Override
    public void run(String... args) {
        // Solo insertar si no hay turnos en la base de datos
        if (turnosRepository.count() > 0) {
            logger.info("Ya existen turnos en la base de datos. Saltando inicialización de agendas médicas.");
            return;
        }

        logger.info("Iniciando generación de agendas médicas...");

        List<Medic> medics = medicRepository.findAll();
        if (medics.isEmpty()) {
            logger.warn("No hay médicos en la base de datos. No se generarán turnos.");
            return;
        }

        int totalTurnos = 0;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(3);

        // Agrupar médicos por especialidad para distribuir centros equitativamente
        Map<MedicalSpecialtyE, List<Medic>> medicsBySpecialty = new HashMap<>();
        for (Medic medic : medics) {
            medicsBySpecialty.computeIfAbsent(medic.getSpecialty(), k -> new ArrayList<>()).add(medic);
        }

        for (Medic medic : medics) {
            // Obtener índice del médico dentro de su especialidad para variar configuraciones
            List<Medic> specialtyMedics = medicsBySpecialty.get(medic.getSpecialty());
            int indexInSpecialty = specialtyMedics.indexOf(medic);
            
            int turnosGenerados = generateTurnosForMedic(medic, startDate, endDate, indexInSpecialty);
            totalTurnos += turnosGenerados;
        }

        logger.info("Inicialización completada. Total de turnos generados: {} para {} médicos", totalTurnos, medics.size());
    }

    private int generateTurnosForMedic(Medic medic, LocalDate startDate, LocalDate endDate, int indexInSpecialty) {
        // Obtener configuración de horarios según la especialidad y posición
        MedicScheduleConfig config = getScheduleConfigForMedic(medic, indexInSpecialty);
        
        List<Turnos> turnosList = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            
            // Verificar si el médico atiende este día
            if (config.workDays.contains(dayOfWeek)) {
                // Generar turnos para cada horario
                for (TimeSlot slot : config.timeSlots) {
                    LocalTime currentTime = slot.startTime;
                    while (currentTime.isBefore(slot.endTime)) {
                        Turnos turno = new Turnos();
                        turno.setFechaTurno(currentDate);
                        turno.setHoraTurno(currentTime);
                        turno.setMedic(medic);
                        turno.setMedicFullName(medic.getName() + " " + medic.getLastname());
                        turno.setMedicSpecialty(medic.getSpecialty());
                        turno.setMedicalCenter(config.medicalCenter);
                        turno.setAvailable(true);
                        
                        turnosList.add(turno);
                        currentTime = currentTime.plusMinutes(config.slotDurationMinutes);
                    }
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        turnosRepository.saveAll(turnosList);
        return turnosList.size();
    }

    /**
     * Configura los horarios de atención para cada médico basándose en su especialidad
     * y su índice dentro de la especialidad para crear variedad.
     */
    private MedicScheduleConfig getScheduleConfigForMedic(Medic medic, int indexInSpecialty) {
        MedicalSpecialtyE specialty = medic.getSpecialty();
        if (specialty == null) {
            specialty = MedicalSpecialtyE.MEDICINA_CLINICA;
        }
        
        // Distribuir centros médicos basándose en el índice
        MedicalCenterE center = CENTERS[indexInSpecialty % CENTERS.length];
        
        // Obtener configuración base según especialidad
        SpecialtyScheduleConfig baseConfig = getSpecialtyBaseConfig(specialty);
        
        // Rotar días de trabajo según el índice del médico
        Set<DayOfWeek> workDays = rotateWorkDays(baseConfig.baseDays, indexInSpecialty);
        
        // Variar horarios según el índice
        List<TimeSlot> timeSlots = getTimeSlots(baseConfig, indexInSpecialty);
        
        return new MedicScheduleConfig(workDays, timeSlots, baseConfig.slotDuration, center);
    }

    /**
     * Configuración base para cada especialidad
     */
    private SpecialtyScheduleConfig getSpecialtyBaseConfig(MedicalSpecialtyE specialty) {
        return switch (specialty) {
            // Especialidades de alta demanda - más días y turnos cortos
            case MEDICINA_CLINICA, PEDIATRIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(8, 0), LocalTime.of(18, 0), 20
            );
            
            // Cardiología - turnos más largos para estudios
            case CARDIOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(8, 0), LocalTime.of(17, 0), 30
            );
            
            // Dermatología - turnos rápidos
            case DERMATOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(9, 0), LocalTime.of(17, 0), 15
            );
            
            // Neurología - turnos más largos para evaluaciones
            case NEUROLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY),
                LocalTime.of(8, 0), LocalTime.of(16, 0), 40
            );
            
            // Traumatología - turnos medianos
            case TRAUMATOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(8, 0), LocalTime.of(18, 0), 30
            );
            
            // Ginecología - turnos medianos
            case GINECOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(8, 0), LocalTime.of(17, 0), 30
            );
            
            // Gastroenterología
            case GASTROENTEROLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(8, 0), LocalTime.of(16, 0), 30
            );
            
            // Cirugía - menos días de consultorio
            case CIRUGIA_GENERAL -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                LocalTime.of(9, 0), LocalTime.of(14, 0), 30
            );
            
            // Psiquiatría - turnos más largos
            case PSIQUIATRIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(9, 0), LocalTime.of(18, 0), 45
            );
            
            // Oftalmología - turnos rápidos
            case OFTALMOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(8, 0), LocalTime.of(17, 0), 20
            );
            
            // Oncología - turnos más largos
            case ONCOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY),
                LocalTime.of(8, 0), LocalTime.of(15, 0), 45
            );
            
            // Endocrinología
            case ENDOCRINOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(8, 0), LocalTime.of(16, 0), 30
            );
            
            // Urología
            case UROLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(8, 0), LocalTime.of(17, 0), 25
            );
            
            // Medicina Interna - similar a clínica
            case MEDICINA_INTERNA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(8, 0), LocalTime.of(17, 0), 30
            );
            
            // Reumatología
            case REUMATOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY),
                LocalTime.of(9, 0), LocalTime.of(16, 0), 30
            );
            
            // Anestesiología - pocos turnos de consultorio
            case ANESTESIOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                LocalTime.of(10, 0), LocalTime.of(13, 0), 30
            );
            
            // Hematología
            case HEMATOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY),
                LocalTime.of(8, 0), LocalTime.of(15, 0), 30
            );
            
            // Radiología - turnos de interpretación
            case RADIOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(8, 0), LocalTime.of(16, 0), 20
            );
            
            // Infectología
            case INFECTOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY),
                LocalTime.of(9, 0), LocalTime.of(16, 0), 30
            );
            
            // Otorrinolaringología - turnos medianos
            case OTORRINOLARINGOLOGIA -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(8, 0), LocalTime.of(17, 0), 25
            );
            
            // Default
            default -> new SpecialtyScheduleConfig(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                LocalTime.of(9, 0), LocalTime.of(14, 0), 30
            );
        };
    }

    /**
     * Rota los días de trabajo basándose en el índice del médico para crear variedad.
     * Por ejemplo, si hay 10 cardiólogos, algunos atenderán L-M-V, otros M-J-V, etc.
     */
    private Set<DayOfWeek> rotateWorkDays(Set<DayOfWeek> baseDays, int index) {
        List<DayOfWeek> daysList = new ArrayList<>(baseDays);
        
        // Crear diferentes patrones según el índice
        int pattern = index % 4;
        
        return switch (pattern) {
            case 0 -> baseDays; // Todos los días base
            case 1 -> {
                // Primeros 3-4 días
                int take = Math.min(daysList.size(), Math.max(3, daysList.size() - 1));
                yield new HashSet<>(daysList.subList(0, take));
            }
            case 2 -> {
                // Últimos 3-4 días
                int skip = Math.max(0, daysList.size() - Math.max(3, daysList.size() - 1));
                yield new HashSet<>(daysList.subList(skip, daysList.size()));
            }
            case 3 -> {
                // Días alternados
                Set<DayOfWeek> alternated = new HashSet<>();
                for (int i = 0; i < daysList.size(); i += 2) {
                    alternated.add(daysList.get(i));
                }
                // Asegurar al menos 2 días
                if (alternated.size() < 2 && daysList.size() > 1) {
                    alternated.add(daysList.get(1));
                }
                yield alternated;
            }
            default -> baseDays;
        };
    }

    /**
     * Genera los slots de tiempo basándose en la configuración y el índice del médico.
     * Algunos médicos trabajan mañana, otros tarde, otros jornada completa.
     */
    private List<TimeSlot> getTimeSlots(SpecialtyScheduleConfig config, int index) {
        int shift = index % 3;
        
        return switch (shift) {
            case 0 -> {
                // Turno mañana
                LocalTime end = config.baseEndTime.isBefore(LocalTime.of(14, 0)) 
                    ? config.baseEndTime 
                    : LocalTime.of(13, 0);
                yield List.of(new TimeSlot(config.baseStartTime, end));
            }
            case 1 -> {
                // Turno tarde
                LocalTime start = config.baseStartTime.isAfter(LocalTime.of(13, 0)) 
                    ? config.baseStartTime 
                    : LocalTime.of(14, 0);
                yield List.of(new TimeSlot(start, config.baseEndTime));
            }
            case 2 -> {
                // Jornada completa o split
                if (config.baseEndTime.isAfter(LocalTime.of(16, 0))) {
                    // Split: mañana y tarde
                    yield List.of(
                        new TimeSlot(config.baseStartTime, LocalTime.of(12, 0)),
                        new TimeSlot(LocalTime.of(14, 0), config.baseEndTime)
                    );
                } else {
                    // Jornada continua
                    yield List.of(new TimeSlot(config.baseStartTime, config.baseEndTime));
                }
            }
            default -> List.of(new TimeSlot(config.baseStartTime, config.baseEndTime));
        };
    }

    /**
     * Configuración de horarios para un médico
     */
    private record MedicScheduleConfig(
        Set<DayOfWeek> workDays,
        List<TimeSlot> timeSlots,
        int slotDurationMinutes,
        MedicalCenterE medicalCenter
    ) {}

    /**
     * Configuración base por especialidad
     */
    private record SpecialtyScheduleConfig(
        Set<DayOfWeek> baseDays,
        LocalTime baseStartTime,
        LocalTime baseEndTime,
        int slotDuration
    ) {}

    /**
     * Representa un rango de horario (inicio y fin)
     */
    private record TimeSlot(LocalTime startTime, LocalTime endTime) {}
}

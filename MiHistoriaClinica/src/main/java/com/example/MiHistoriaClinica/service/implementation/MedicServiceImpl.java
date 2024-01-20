package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.util.constant.MedicalSpecialtyE;
import com.example.MiHistoriaClinica.util.exception.MedicNotFoundException;
import com.example.MiHistoriaClinica.util.exception.PatientNotFoundException;
import com.example.MiHistoriaClinica.util.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.persistence.model.MedicalFile;
import com.example.MiHistoriaClinica.persistence.repository.*;
import com.example.MiHistoriaClinica.persistence.model.Medic;
import com.example.MiHistoriaClinica.persistence.model.Medicine;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.presentation.dto.*;
import com.example.MiHistoriaClinica.service.MedicService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicServiceImpl implements MedicService {

    private final MedicRepository medicRepository;
    private final PatientRepository patientRepository;
    private final MedicineRepository medicineRepository;
    private final AnalysisRepository analysisRepository;
    private final MedicalFileRepository medicalFileRepository;

    private final CustomRepositoryAccess customRepositoryAccess;
    private final PatientServiceImpl patientService;


    public MedicServiceImpl(MedicRepository medicRepository, PatientRepository patientRepository, MedicineRepository medicineRepository, AnalysisRepository analysisRepository, MedicalFileRepository medicalFileRepository, CustomRepositoryAccess customRepositoryAccess, PatientServiceImpl patientService) {
        this.medicRepository = medicRepository;
        this.patientRepository = patientRepository;
        this.medicineRepository = medicineRepository;
        this.analysisRepository = analysisRepository;
        this.medicalFileRepository = medicalFileRepository;

        this.customRepositoryAccess = customRepositoryAccess;
        this.patientService = patientService;
    }


    /** Métodos Médico*/

    @Override
    public Medic createMedic(MedicDTO medic) {
       return customRepositoryAccess.saveMedicDto(medic);
    }

    /**
     * En vez de devolver un objeto médico, devolves un objeto que es {token: (token generado por el jwt)}
     * Rochi va a guardar ese token en el local storage y lo va a usar para hacer las peticiones a los endpoints
     * Rochi lo debería mandar como "Bearer token"
     * **/
    @Override
    public Medic loginMedic(MedicLoginDTO medic) {

        Medic result = medicRepository.findByMatriculaAndPassword(medic.getMatricula(), medic.getPassword());
        if (result == null) {
            throw new MedicNotFoundException();
        } else {
            return result;
        }
    }

    @Override
    public Medic getMedicById(Long id) {
        return medicRepository.findById(id).orElse(null);
    }

    @Override
    public Medic updateMedic(Long id, Medic newMedic) {
        Medic medic = medicRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Medic not found"));

        medic.setName(newMedic.getName());
        medic.setLastname(newMedic.getLastname());
        medic.setDni(newMedic.getDni());
        medic.setEmail(newMedic.getEmail());
        medic.setMatricula(newMedic.getMatricula());
        medic.setSpecialty(newMedic.getSpecialty());
        medic.setPassword(newMedic.getPassword());

        return medicRepository.save(medic);
    }

    @Override
    public List<Medic> getAllMedics() {
        return medicRepository.findAll();
    }



    /***********     Metodos médico-paciente     *********/

    /**
     * Este método recibe el código de enlace y el identificador del médico, y utiliza los repositorios de Medic y Patient
     * para obtener los registros correspondientes.
     * Luego, asocia al paciente al conjunto de pacientes del médico y guarda el registro del médico actualizado.
     */
    @Transactional
    public void linkPatient(String linkCode, Long medicId) {
        Medic medic = medicRepository.findById(medicId).orElse(null);
        if (medic == null) {
            throw new RuntimeException("No se pudo asociar el paciente. El médico no existe.");
        }
        Optional<Patient> patientOptional = patientRepository.findByLinkCode(linkCode);
        if (patientOptional.isEmpty()) {
            throw new RuntimeException("No se pudo asociar el paciente. El código de enlace no es válido.");
        }

        Patient patient = patientOptional.get();

        medic.getPatients().add(patient);
        patient.getMedics().add(medic);  // Agregar el médico a la lista de médicos del paciente

        medicRepository.save(medic);
        patientRepository.save(patient);  // Guardar también al paciente para actualizar la relación
    }//todo checkeo del que el link no exista

    private boolean isPatientLinked(Long medicId, String linkCode) {

        //obtengo los pacientes de este médico
        List<Patient> patients = getPatientsByMedicId(medicId);

        Optional<Patient> auxPatient = patientRepository.findByLinkCode(linkCode);

        //checkeo si el paciente ya fue linkeado
        return auxPatient.isPresent() && patients.contains(auxPatient.get());
    }

    @Override
    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> getPatientByLinkCode(String patientLinkCode) {
        return patientRepository.findByLinkCode(patientLinkCode);
    }

    @Override
    public List<Patient> getPatientsByMedicId(Long medicId){
        return medicRepository.getPatientsByMedicId(medicId);
    }

    @Override
    public List<PatientDTO> getPatientsDtoByMedicId(Long medicId) {
        List<Patient> patients = medicRepository.getPatientsByMedicId(medicId);

        ModelMapper modelMapper = new ModelMapper();

        return patients.stream()
                .map(patientModel -> modelMapper.map(patientModel, PatientDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MedicDTO getMedicInfo(Long medicId) {

        ModelMapper modelMapper = new ModelMapper();
        Medic medic = getMedicById(medicId);

        return modelMapper.map(medic, MedicDTO.class);
    }

    @Override
    public PatientDTO getPatientInfo(String patientLinkcode) {
        return patientService.getPatientInfo(patientRepository.findByLinkCode(patientLinkcode).get().getPatientId());
    }

    @Override
    public void deletePatientLinkCode(String patientLinkCode) {
        Patient patient = patientRepository.findByLinkCode(patientLinkCode)
                .orElseThrow(() -> new PatientNotFoundException());

        patient.setLinkCode(null);
        patientRepository.save(patient);
    }

    /** Métodos MedicalFile*/

    @Transactional
    @Override
    public MedicalFile createPatientMedicalHistory(Long medicId, String linkCode, MedicalFileDTO medicalHistory) {

        Optional<Medic> medic = medicRepository.findById(medicId);
        Optional<Patient> patient = patientRepository.findByLinkCode(linkCode);

        if (medic.isEmpty() || patient.isEmpty() || !isPatientLinked(medicId, linkCode)) return null;
        else                return customRepositoryAccess.auxMedicalHistory(patient, medicalHistory);

    }

    /**
     * este método va a obtener la historia clínica de un paciente determinado
     * primero checkea que el médico y el paciente estén linkeados
     */
    @Override
    public MedicalFileDTO getPatientMedicalHistory(String linkCode) {
        Patient patient = patientRepository.findByLinkCode(linkCode).get();
        return patientService.getMedicalHistory(patient.getPatientId());
    }
/*
    public MedicalHistoryModel updatePatientMedicalHistory(Long medicId, String patientLinkCode, MedicalFileDTO update) {
        Optional<MedicModel> medic = medicRepository.findById(medicId);
        Optional<PatientModel> patient = patientRepository.findByLinkCode(patientLinkCode);

        if (medic.isEmpty() || patient.isEmpty() || !isPatientLinked(medicId, patientLinkCode))     return  null;
        else                                    return customRepositoryAccess.auxMedicalHistory(patient, update);
        
    }
*/
    /** Métodos Medicines*/

    @Override
    public Medicine createPatientMedicine(Long medicId, String patientLinkCode, MedicineDTO medicine) {

        Optional<Medic> medic = medicRepository.findById(medicId);
        Optional<Patient> patient = patientRepository.findByLinkCode(patientLinkCode);

        if(medic.isEmpty() || patient.isEmpty() || !isPatientLinked(medicId, patientLinkCode))    return null;
        else    return customRepositoryAccess.createPatientMedicine(medicine, patient);
    }

    @Override
    public List<Medicine> getMedicinesByPatientLinkCode(String patientLinkCode) {

        Optional<Patient> patient = patientRepository.findByLinkCode(patientLinkCode);
        List<Medicine> medicines = patient.get().getMedicines();

        if(medicines == null)       return null;
        else                        return medicines;
    }

    @Override
    public void deletePatientMedicine(String patientLinkCode, Long medicineId) {
        Optional<Patient> patientOptional = getPatientByLinkCode(patientLinkCode);

        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            List<Medicine> medicines = patient.getMedicines();

            // Encuentra la medicina con el ID proporcionado
            Medicine medicineToDelete = medicines.stream()
                    .filter(medicine -> medicine.getMedicineId().equals(medicineId))
                    .findFirst()
                    .orElse(null);

            if (medicineToDelete != null) {
                medicines.remove(medicineToDelete);
                savePatient(patient);
            }
        }
    }

    public List<Medicine> getAnalysisByStatus(String patientLinkCode, String status) {
        Optional<Patient> patient = patientRepository.findByLinkCode(patientLinkCode);
        return patientRepository.getMedicinesByPatientIdAndStatus(patient.get().getPatientId(), status);
    }

    public MedicalFileRepository getMedicalHistoryRepository() {
        return medicalFileRepository;
    }

    @Override
    public List<String> getAllSpecialties() {
        return MedicalSpecialtyE.getSpecialties();
    }
}

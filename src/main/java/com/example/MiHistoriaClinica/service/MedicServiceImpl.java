package com.example.MiHistoriaClinica.service;

import com.example.MiHistoriaClinica.dto.MedicSignupDTO;
import com.example.MiHistoriaClinica.exception.MedicNotFoundException;
import com.example.MiHistoriaClinica.exception.ResourceNotFoundException;
import com.example.MiHistoriaClinica.model.*;
import com.example.MiHistoriaClinica.repository.*;
import com.example.MiHistoriaClinica.service.interfaces.MedicService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicServiceImpl implements MedicService {

    private final MedicRepository medicRepository;
    private final PatientRepository patientRepository;
    private final MedicineRepository medicineRepository;
    private final AnalysisRepository analysisRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;

    private final CustomRepositoryAccess customRepositoryAccess;



    public MedicServiceImpl(MedicRepository medicRepository, PatientRepository patientRepository, MedicineRepository medicineRepository, AnalysisRepository analysisRepository, MedicalHistoryRepository medicalHistoryRepository, CustomRepositoryAccess customRepositoryAccess) {
        this.medicRepository = medicRepository;
        this.patientRepository = patientRepository;
        this.medicineRepository = medicineRepository;
        this.analysisRepository = analysisRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;

        this.customRepositoryAccess = customRepositoryAccess;
    }


    @Override
    public MedicModel createMedic(MedicSignupDTO medic) {
       return customRepositoryAccess.saveMedicDto(medic);
    }

    /**
     * En vez de devolver un objeto médico, devolves un objeto que es {token: (token generado por el jwt)}
     * Rochi va a guardar ese token en el local storage y lo va a usar para hacer las peticiones a los endpoints
     * Rochi lo deberia mandar como "Bearer token"
     * **/
    @Override
    public MedicModel loginMedic(MedicModel medic) {

        MedicModel result = medicRepository.findByMatriculaAndPassword(medic.getMatricula(), medic.getPassword());
        if (result == null) {
            throw new MedicNotFoundException();
        } else {
            return result;
        }
    }

    @Override
    public MedicModel getMedicById(Long id) {
        return medicRepository.findById(id).orElse(null);
    }

    @Override
    public MedicModel getMedicByDni(Long dni) {
        MedicModel medic = medicRepository.findByDni(dni);
        if(medic == null) {
            throw new MedicNotFoundException();
        } else {
            return medic;
        }
    }

    @Override
    public ArrayList<MedicModel> getAllMedic() {
        return (ArrayList<MedicModel>) medicRepository.findAll();
    }

    @Override
    public MedicModel updateMedic(Long id, MedicModel newMedic) {
        MedicModel medic = medicRepository.findById(id).orElseThrow(()
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
    public ResponseEntity<Void> deleteMedic(Long id) {
        MedicModel medic = medicRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Doctor not found"));
        medicRepository.delete(medic);
        return ResponseEntity.noContent().build();

    }

    @Override
    public ResponseEntity<Void> deleteMedicByDni(Long dni) {
       return medicRepository.deleteByDni(dni);
    }

    @Override
    public void deleteAllMedic() {
        medicRepository.deleteAll();
    }

    /**
     * Este método recibe el código de enlace y el identificador del médico, y utiliza los repositorios de Medic y Patient
     * para obtener los registros correspondientes.
     * Luego, asocia al paciente al conjunto de pacientes del médico y guarda el registro del médico actualizado.
     */
    @Transactional
    public void linkPatient(String linkCode, Long medicId) {
        MedicModel medic = medicRepository.findById(medicId).orElse(null);
        if (medic == null) {
            throw new RuntimeException("No se pudo asociar el paciente. El médico no existe.");
        }
        PatientModel patient = patientRepository.findByLinkCode(linkCode);
        if (patient == null) {
            throw new RuntimeException("No se pudo asociar el paciente. El código de enlace no es válido.");
        }

        medic.getPatients().add(patient);
        patient.getMedics().add(medic);  // Agregar el médico a la lista de médicos del paciente

        medicRepository.save(medic);
        patientRepository.save(patient);  // Guardar también al paciente para actualizar la relación
    }//todo checkeo del que el link no exista

    @Override
    public MedicineModel addMedicine(MedicineModel medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public AnalysisModel addAnalysis(AnalysisModel analysis) {
        return analysisRepository.save(analysis);
    }

    @Override
    public MedicalHistoryModel createMedicalHistory(MedicalHistoryModel history) {
        return medicalHistoryRepository.save(history);
    }

    public List<PatientModel> getPatientsByMedicId(Long medicId) {
        return medicRepository.getPatientsByMedicId(medicId);
    }

}

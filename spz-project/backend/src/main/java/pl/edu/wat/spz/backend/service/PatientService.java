package pl.edu.wat.spz.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.spz.backend.repository.PatientRepository;
import pl.edu.wat.spz.domain.entity.Patient;

import java.util.Optional;

@Service
public class PatientService {

    private PatientRepository repository;

    @Autowired
    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public Optional<Patient> findById(Long id) {
        return repository.findById(id);
    }

    public Patient update(Patient patient) {
        Optional<Patient> patientOptional = findById(patient.getId());
        if (patientOptional.isPresent()) {
            Patient uPatient = patientOptional.get();

            uPatient.setFirstName(patient.getFirstName());
            uPatient.setLastName(patient.getLastName());
            uPatient.setNationalId(patient.getNationalId());
            uPatient.setIdCardNo(patient.getIdCardNo());
            uPatient.setAddress(patient.getAddress());
            uPatient.setPostalCode(patient.getPostalCode());
            uPatient.setCity(patient.getCity());
            uPatient.setMobileNumber(patient.getMobileNumber());

            return repository.save(uPatient);
        }

        return null;
    }
}

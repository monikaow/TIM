package pl.edu.wat.spz.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.spz.backend.repository.DoctorRepository;
import pl.edu.wat.spz.domain.entity.Doctor;

import java.util.Optional;

@Service
public class DoctorService {

    private DoctorRepository repository;

    @Autowired
    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public Optional<Doctor> findById(Long id) {
        return repository.findById(id);
    }

    public Doctor update(Doctor doctor) {
        Optional<Doctor> patientOptional = findById(doctor.getId());
        if (patientOptional.isPresent()) {
            Doctor uDoctor = patientOptional.get();

            uDoctor.setTitle(doctor.getTitle());
            uDoctor.setFirstName(doctor.getFirstName());
            uDoctor.setLastName(doctor.getLastName());
            uDoctor.setNationalId(doctor.getNationalId());
            uDoctor.setIdCardNo(doctor.getIdCardNo());
            uDoctor.setAddress(doctor.getAddress());
            uDoctor.setPostalCode(doctor.getPostalCode());
            uDoctor.setCity(doctor.getCity());
            uDoctor.setMobileNumber(doctor.getMobileNumber());

            return repository.save(uDoctor);
        }

        return null;
    }
}

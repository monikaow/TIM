package pl.edu.wat.spz.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.spz.backend.repository.DoctorSpecializationRepository;
import pl.edu.wat.spz.domain.entity.DoctorSpecialization;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorSpecializationService {

    private DoctorSpecializationRepository repository;

    @Autowired
    public DoctorSpecializationService(DoctorSpecializationRepository repository) {
        this.repository = repository;
    }

    public Optional<DoctorSpecialization> findById(Long id) {
        return repository.findById(id);
    }

    public List<DoctorSpecialization> findAllBySpecializationId(Long specializationId) {
        return repository.findAllBySpecializationId(specializationId);
    }
}

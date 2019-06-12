package pl.edu.wat.spz.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.spz.backend.repository.SpecializationRepository;
import pl.edu.wat.spz.domain.entity.Specialization;

import java.util.List;

@Service
public class SpecializationService {

    private SpecializationRepository repository;

    @Autowired
    public SpecializationService(SpecializationRepository repository) {
        this.repository = repository;
    }

    public List<Specialization> findAll() {
        return repository.findAll();
    }
}
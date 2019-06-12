package pl.edu.wat.spz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.spz.domain.entity.DoctorSpecialization;

import java.util.List;

@Repository
public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, Long> {

    List<DoctorSpecialization> findAllBySpecializationId(Long specializationId);
}

package pl.edu.wat.spz.backend.repository;

import org.springframework.stereotype.Repository;
import pl.edu.wat.spz.domain.entity.Doctor;

@Repository
public interface DoctorRepository extends UserBaseRepository<Doctor> {

}

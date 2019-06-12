package pl.edu.wat.spz.backend.repository;

import org.springframework.stereotype.Repository;
import pl.edu.wat.spz.domain.entity.Patient;

@Repository
public interface PatientRepository extends UserBaseRepository<Patient> {

}

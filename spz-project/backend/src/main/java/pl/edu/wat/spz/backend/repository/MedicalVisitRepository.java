package pl.edu.wat.spz.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.wat.spz.domain.entity.MedicalVisit;

import java.util.List;

@Repository
public interface MedicalVisitRepository extends JpaRepository<MedicalVisit, Long> {

    @Query("SELECT mv FROM MedicalVisit mv " +
            "WHERE mv.doctorSpecialization.doctor.id = :doctorId " +
            "AND SUBSTRING(function('to_char', mv.doctorTimetable.date, :date_format), 1, 10) = :date " +
            "AND mv.cancel = false " +
            "ORDER BY mv.doctorTimetable.date asc")
    List<MedicalVisit> findByDoctorAndDate(@Param("doctorId") Long doctorId,
                                           @Param("date") String date,
                                           @Param("date_format") String dateFormat);

    @Query("SELECT mv FROM MedicalVisit mv WHERE mv.patient.id = :patientId and mv.cancel = false")
    Page<MedicalVisit> findByPatientPaginated(@Param("patientId") Long patientId,
                                              Pageable pageable);

    @Modifying
    @Query("UPDATE MedicalVisit mv SET mv.cancel = true WHERE mv.id = :medicalVisitId")
    void cancelVisitById(@Param("medicalVisitId") Long medicalVisitId );
}
package pl.edu.wat.spz.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.wat.spz.domain.entity.RecipeHistory;

@Repository
public interface RecipeHistoryRepository extends JpaRepository<RecipeHistory, Long> {

    @Query("SELECT rh from RecipeHistory rh WHERE rh.medicalVisit.patient.id = :patientId")
    Page<RecipeHistory> findAllByPatientId(@Param("patientId") Long patientId, Pageable pageable);
}
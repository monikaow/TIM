package pl.edu.wat.spz.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.wat.spz.domain.entity.DoctorTimetable;

import java.util.List;

@Repository
public interface DoctorTimetableRepository extends JpaRepository<DoctorTimetable, Long> {

    @Query(value = "SELECT dt " +
            "FROM DoctorTimetable AS dt " +
            "WHERE dt.doctor.id = :doctorId AND dt.date > current_timestamp " +
            "AND SUBSTRING(function('to_char', dt.date, :date_format), 1, 10) = :date AND dt.enable = true " +
            "ORDER BY dt.date")
    List<DoctorTimetable> findByDoctorAndDate(@Param("doctorId") Long doctorId, @Param("date") String date, @Param("date_format") String dateFormat);

    @Query(value = "SELECT dt " +
            "FROM DoctorTimetable AS dt " +
            "WHERE dt.doctor.id = :doctorId AND dt.date > current_timestamp AND dt.enable = true " +
            "ORDER BY dt.date ASC")
    Page<DoctorTimetable> findFirstEnableVisit(@Param("doctorId") Long doctorId, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE DoctorTimetable dt SET dt.enable = true WHERE dt.id = :doctorTimetableId")
    void enableDoctorTimetableById(@Param("doctorTimetableId") Long doctorTimetableId);

}

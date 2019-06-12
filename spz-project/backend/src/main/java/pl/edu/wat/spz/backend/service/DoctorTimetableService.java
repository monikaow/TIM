package pl.edu.wat.spz.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.spz.backend.repository.DoctorTimetableRepository;
import pl.edu.wat.spz.commons.utils.Constans;
import pl.edu.wat.spz.domain.entity.DoctorTimetable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorTimetableService {

    private SimpleDateFormat sdf;

    private DoctorTimetableRepository repository;

    @Autowired
    public DoctorTimetableService(SimpleDateFormat sdf, DoctorTimetableRepository repository) {
        this.sdf = sdf;
        this.repository = repository;
    }

    public List<DoctorTimetable> findByDoctorAndDate(Long doctorId, Date date) {
        return repository.findByDoctorAndDate(doctorId, sdf.format(date), Constans.DATE_FORMAT);
    }

    @Transactional
    void enableDoctorTimetableById(Long doctorTimetableId) {
        repository.enableDoctorTimetableById(doctorTimetableId);
    }

    public List<List<DoctorTimetable>> groupByDoctorAndDates(Long doctorId, List<Date> dates) {
        List<List<DoctorTimetable>> list = new ArrayList<>();
        for (Date date : dates) {
            list.add(findByDoctorAndDate(doctorId, date));
        }
        return list;
    }

    public Optional<DoctorTimetable> findById(Long doctorTimetableId) {
        return repository.findById(doctorTimetableId);
    }

    public DoctorTimetable findFirstEnableVisit(Long doctorTimetableId) {
        List<DoctorTimetable> content = repository.findFirstEnableVisit(doctorTimetableId, PageRequest.of(0, 1)).getContent();

        DoctorTimetable doctorTimetable = null;

        if (!content.isEmpty()) {
            doctorTimetable = content.get(0);
        }

        return doctorTimetable;


    }

    @Transactional
    public DoctorTimetable save(DoctorTimetable timetable) {
        return repository.save(timetable);
    }
}

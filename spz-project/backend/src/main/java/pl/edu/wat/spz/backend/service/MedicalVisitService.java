package pl.edu.wat.spz.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.LocaleResolver;
import pl.edu.wat.spz.backend.dto.MedicalVisitDto;
import pl.edu.wat.spz.backend.events.CancelMedicalVisitEvent;
import pl.edu.wat.spz.backend.events.RegisterMedicalVisitEvent;
import pl.edu.wat.spz.backend.repository.MedicalVisitRepository;
import pl.edu.wat.spz.commons.utils.Constans;
import pl.edu.wat.spz.domain.entity.DoctorSpecialization;
import pl.edu.wat.spz.domain.entity.DoctorTimetable;
import pl.edu.wat.spz.domain.entity.MedicalVisit;
import pl.edu.wat.spz.domain.entity.Patient;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalVisitService {

    private SimpleDateFormat sdf;

    private MedicalVisitRepository repository;

    private PatientService patientService;

    private DoctorTimetableService timetableService;

    private DoctorSpecializationService doctorSpecializationService;

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public MedicalVisitService(SimpleDateFormat sdf, MedicalVisitRepository repository, PatientService patientService, DoctorTimetableService timetableService, DoctorSpecializationService doctorSpecializationService, ApplicationEventPublisher applicationEventPublisher) {
        this.sdf = sdf;
        this.repository = repository;
        this.patientService = patientService;
        this.timetableService = timetableService;
        this.doctorSpecializationService = doctorSpecializationService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public MedicalVisit registerMedicalVisit(MedicalVisitDto visit) {
        MedicalVisit medicalVisit = new MedicalVisit();

        Optional<Patient> patient = patientService.findById(visit.getPatientId());
        Optional<DoctorTimetable> doctorTimetable = timetableService.findById(visit.getDoctorTimetableId());
        Optional<DoctorSpecialization> doctorSpecialization = doctorSpecializationService.findById(visit.getDoctorSpecializationId());

        if (patient.isPresent() && doctorTimetable.isPresent() && doctorSpecialization.isPresent()) {
            DoctorTimetable timetable = doctorTimetable.get();
            timetable.setEnable(false);
            timetable = timetableService.save(timetable);


            medicalVisit.setPatient(patient.get());
            medicalVisit.setDoctorTimetable(timetable);
            medicalVisit.setDoctorSpecialization(doctorSpecialization.get());

            medicalVisit = repository.save(medicalVisit);

            applicationEventPublisher.publishEvent(new RegisterMedicalVisitEvent(this, medicalVisit));
        }

        return medicalVisit;
    }

    public List<MedicalVisit> findAllByDoctorId(Long doctorId, Date date) {
        return repository.findByDoctorAndDate(doctorId, sdf.format(date), Constans.DATE_FORMAT);
    }

    public Page<MedicalVisit> findByPatientPaginated(Long patientId, Pageable pageable) {
        return repository.findByPatientPaginated(patientId, pageable);
    }

    @Transactional
    public MedicalVisit cancelVisit(Long medicalVisitId) {
        repository.cancelVisitById(medicalVisitId);
        MedicalVisit medicalVisit = repository.findById(medicalVisitId).get();
        timetableService.enableDoctorTimetableById(medicalVisit.getDoctorTimetable().getId());

        applicationEventPublisher.publishEvent(new CancelMedicalVisitEvent(this, medicalVisit));
        return medicalVisit;
    }

    public Optional<MedicalVisit> findById(Long medicalVisitId) {
        return repository.findById(medicalVisitId);
    }

    @Transactional
    public MedicalVisit updateVisit(Long medicalVisitId, Long timetableId) {
        MedicalVisit medicalVisit = cancelVisit(medicalVisitId);
        MedicalVisitDto dto = new MedicalVisitDto();
        dto.setPatientId(medicalVisit.getPatient().getId());
        dto.setDoctorSpecializationId(medicalVisit.getDoctorSpecialization().getId());
        dto.setDoctorTimetableId(timetableId);
        return registerMedicalVisit(dto);
    }
}

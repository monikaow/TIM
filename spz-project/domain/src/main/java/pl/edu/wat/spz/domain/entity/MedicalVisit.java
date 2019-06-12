package pl.edu.wat.spz.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import pl.edu.wat.spz.domain.entity.base.BaseEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "T_MEDICAL_VISIT")
@SequenceGenerator(name = "S_MEDICAL_VISIT", initialValue = 100, sequenceName = "SEQ_MEDICAL_VISIT", allocationSize = 1)
public class MedicalVisit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_MEDICAL_VISIT")
    private Long id;

    @ManyToOne
    @NotFound(action = NotFoundAction.EXCEPTION)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @NotFound(action = NotFoundAction.EXCEPTION)
    @JoinColumn(name = "doctor_specialization_id")
    private DoctorSpecialization doctorSpecialization;

    @ManyToOne
    @NotFound(action = NotFoundAction.EXCEPTION)
    @JoinColumn(name = "doctor_timetable_id")
    private DoctorTimetable doctorTimetable;

    private boolean cancel;
}

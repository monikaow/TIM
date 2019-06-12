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
@Table(name = "T_DOCTOR_SPECIALIZATION")
@SequenceGenerator(name = "S_DOCTOR_SPECIALIZATION", initialValue = 100, sequenceName = "SEQ_DOCTOR_SPECIALIZATION", allocationSize = 1)
public class DoctorSpecialization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_DOCTOR_SPECIALIZATION")
    private Long id;

    @ManyToOne
    @NotFound(action = NotFoundAction.EXCEPTION)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @NotFound(action = NotFoundAction.EXCEPTION)
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

}

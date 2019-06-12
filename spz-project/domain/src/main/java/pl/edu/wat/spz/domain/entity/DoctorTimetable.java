package pl.edu.wat.spz.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import pl.edu.wat.spz.commons.utils.Constans;
import pl.edu.wat.spz.domain.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "T_DOCTOR_TIMETABLE")
@SequenceGenerator(name = "S_DOCTOR_TIMETABLE", initialValue = 100, sequenceName = "SEQ_DOCTOR_TIMETABLE", allocationSize = 1)
public class DoctorTimetable extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_DOCTOR_TIMETABLE")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @NotFound(action = NotFoundAction.EXCEPTION)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constans.DATE_TIME_FORMAT)
    private Date date;

    private boolean enable;

}

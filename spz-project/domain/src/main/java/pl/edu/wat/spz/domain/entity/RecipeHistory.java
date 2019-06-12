package pl.edu.wat.spz.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "T_RECIPE_HISTORY")
@SequenceGenerator(name = "S_RECIPE_HISTORY", initialValue = 100, sequenceName = "SEQ_RECIPE_HISTORY", allocationSize = 1)
public class RecipeHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_RECIPE_HISTORY")
    private Long id;

    @ManyToOne
    @NotFound(action = NotFoundAction.EXCEPTION)
    @JoinColumn(name = "medical_visit_id")
    private MedicalVisit medicalVisit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constans.DATE_TIME_FORMAT)
    private Date receiptDate;

    private String medicineName;

    private String comment;
}

package pl.edu.wat.spz.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "T_DOCTOR")
public class Doctor extends User {

    private String title;

    private BigDecimal price;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name = "t_doctor_specialization", uniqueConstraints = {@UniqueConstraint(columnNames = {"doctor_id", "specialization_id"})},
            joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "specialization_id", referencedColumnName = "id"))
    private List<Specialization> specializations = new ArrayList<>();

}

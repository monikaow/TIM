package pl.edu.wat.spz.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.edu.wat.spz.domain.entity.base.BaseEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "T_SPECIALIZATION")
@SequenceGenerator(name = "S_SPECIALIZATION", initialValue = 100, sequenceName = "SEQ_SPECIALIZATION", allocationSize = 1)
public class Specialization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_SPECIALIZATION")
    private Long id;

    @Column(unique = true)
    private String name;
}

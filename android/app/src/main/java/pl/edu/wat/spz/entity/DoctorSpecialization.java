package pl.edu.wat.spz.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorSpecialization implements Serializable {

    private String id;

    private Doctor doctor;

    private Specialization specialization;
}

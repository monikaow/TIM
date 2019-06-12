package pl.edu.wat.spz.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalVisit implements Serializable {

    private String id;

    private DoctorTimetable doctorTimetable;

    private DoctorSpecialization doctorSpecialization;

    private boolean cancel;

    public String getDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("Lekarz: ").append(doctorSpecialization.getDoctor().getFullTitle()).append("\n");
        builder.append("Specjalizacja: ").append(doctorSpecialization.getSpecialization().getName()).append("\n");
        builder.append("Wizyta: ").append(doctorTimetable.getDate()).append("\n");
        builder.append("Cena: ").append(doctorSpecialization.getDoctor().getPrice()).append(" zł");

        return builder.toString();
    }
}

package pl.edu.wat.spz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalVisitDto {

    String patientId;

    String doctorSpecializationId;

    String doctorTimetableId;

    public MedicalVisitDto(String patientId, String doctorSpecializationId, String doctorTimetableId) {
        this.patientId = patientId;
        this.doctorSpecializationId = doctorSpecializationId;
        this.doctorTimetableId = doctorTimetableId;
    }
}

package pl.edu.wat.spz.backend.dto;

import lombok.Data;

@Data
public class MedicalVisitDto {

    Long patientId;

    Long doctorSpecializationId;

    Long doctorTimetableId;
}

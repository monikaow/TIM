package pl.edu.wat.spz.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import pl.edu.wat.spz.commons.utils.Constans;

import java.util.Date;

@Data
public class RecipeHistoryDto {

    private Long medicalVisitId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constans.DATE_TIME_FORMAT)
    private Date receiptDate;

    private String medicineName;

    private String comment;
}

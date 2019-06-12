package pl.edu.wat.spz.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeHistory implements Serializable {

    private String id;

    private MedicalVisit medicalVisit;

    private String receiptDate;

    private String medicineName;

    private String comment;

    public String getDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("Stosowanie: ").append(comment).append("\n");
        builder.append("Do odbioru: ").append(receiptDate).append("\n");
        return builder.toString();
    }
}

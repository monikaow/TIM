package pl.edu.wat.spz.backend.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.spz.domain.entity.MedicalVisit;

import javax.servlet.http.HttpServletRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterMedicalVisitEvent extends BaseEmailApplicationEvent<MedicalVisit> {

    private MedicalVisit medicalVisit;

    public RegisterMedicalVisitEvent(Object source, MedicalVisit medicalVisit) {
        super(source);
        this.medicalVisit = medicalVisit;
    }
}
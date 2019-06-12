package pl.edu.wat.spz.backend.events.listeners;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import pl.edu.wat.spz.backend.events.CancelMedicalVisitEvent;
import pl.edu.wat.spz.domain.entity.MedicalVisit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Component
public class CancelMedicalVisitEventListener extends BaseEmailApplicationListener<CancelMedicalVisitEvent> {

    @Override
    public void onApplicationEvent(final CancelMedicalVisitEvent event) {
        try {
            final MimeMessage email = constructAndSendEmailMessage(event);
            getEmailSender().send(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MimeMessage constructAndSendEmailMessage(final CancelMedicalVisitEvent event) throws MessagingException, IOException, TemplateException {
        ModelMap model = new ModelMap();
        final MedicalVisit medicalVisit = event.getMedicalVisit();
        model.put("medicalVisit", medicalVisit);

        return constructEmailMessage("/mail/cancel-medical-visit",
                model,
                medicalVisit.getPatient().getEmail(),
                event.getRequest(),
                "email.subject.cancel.visit");
    }
}
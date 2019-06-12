package pl.edu.wat.spz.backend.events.listeners;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.LocaleResolver;
import pl.edu.wat.spz.backend.events.RegisterMedicalVisitEvent;
import pl.edu.wat.spz.domain.entity.MedicalVisit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Component
public class RegisterMedicalVisitEventListener extends BaseEmailApplicationListener<RegisterMedicalVisitEvent> {

    @Override
    public void onApplicationEvent(final RegisterMedicalVisitEvent event) {
        try {
            final MimeMessage email = constructAndSendEmailMessage(event);
            getEmailSender().send(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MimeMessage constructAndSendEmailMessage(final RegisterMedicalVisitEvent event) throws MessagingException, IOException, TemplateException {
        ModelMap model = new ModelMap();
        final MedicalVisit medicalVisit = event.getMedicalVisit();
        model.put("medicalVisit", medicalVisit);

        return constructEmailMessage("/mail/register-medical-visit",
                model,
                medicalVisit.getPatient().getEmail(),
                event.getRequest(),
                "email.subject.confirm.visit");
    }
}

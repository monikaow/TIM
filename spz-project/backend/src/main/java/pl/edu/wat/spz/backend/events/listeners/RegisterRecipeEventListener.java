package pl.edu.wat.spz.backend.events.listeners;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import pl.edu.wat.spz.backend.events.RegisterRecipeEvent;
import pl.edu.wat.spz.domain.entity.RecipeHistory;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.UUID;

@Component
public class RegisterRecipeEventListener extends BaseEmailApplicationListener<RegisterRecipeEvent> {

    @Override
    public void onApplicationEvent(final RegisterRecipeEvent event) {
        try {
            final MimeMessage email = constructAndSendEmailMessage(event);
            getEmailSender().send(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MimeMessage constructAndSendEmailMessage(final RegisterRecipeEvent event) throws MessagingException, IOException, TemplateException {
        ModelMap model = new ModelMap();
        final RecipeHistory recipeHistory = event.getRecipeHistory();
        model.put("recipeHistory", recipeHistory);

        return constructEmailMessage("/mail/register-recipe",
                model,
                recipeHistory.getMedicalVisit().getPatient().getEmail(),
                event.getRequest(),
                "email.subject.recipe");
    }
}
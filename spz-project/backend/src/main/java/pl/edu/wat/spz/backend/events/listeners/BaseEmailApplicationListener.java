package pl.edu.wat.spz.backend.events.listeners;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.LocaleResolver;
import pl.edu.wat.spz.backend.beans.MessageResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Data
public abstract class BaseEmailApplicationListener<T extends ApplicationEvent> implements ApplicationListener<T> {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Autowired
    private Environment env;

    @Autowired
    private MessageResolver messageResolver;

    @Autowired
    private LocaleResolver localeResolver;


    public abstract MimeMessage constructAndSendEmailMessage(T t) throws MessagingException, IOException, TemplateException;

    protected MimeMessage constructEmailMessage(String template, ModelMap model, String sendTo, HttpServletRequest request, String subject) throws MessagingException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());


        Template t = freemarkerConfig.getTemplate(template + "_" + localeResolver.resolveLocale(request).toLanguageTag() + ".ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        helper.setTo(sendTo);
        helper.setSubject(messageResolver.resolveMessage(subject));
        helper.setText(html, true);
        helper.setFrom(env.getProperty("spring.mail.username"));

        return message;
    }

}

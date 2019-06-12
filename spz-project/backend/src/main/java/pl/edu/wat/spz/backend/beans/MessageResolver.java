package pl.edu.wat.spz.backend.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;

@Component
public class MessageResolver {

    @Qualifier("messageSource")
    @Autowired
    MessageSource messages;

    @Autowired
    private LocaleResolver localeResolver;

    public String resolveMessage(String code) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return messages.getMessage(code, null, localeResolver.resolveLocale(request));
    }
}

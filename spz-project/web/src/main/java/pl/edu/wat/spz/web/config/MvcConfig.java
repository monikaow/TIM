package pl.edu.wat.spz.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import pl.edu.wat.spz.domain.entity.audit.AuditorAwareImpl;
import pl.edu.wat.spz.web.handlers.PatientHandlerInterceptor;

@EnableJpaAuditing(auditorAwareRef="auditorProvider")
@Configuration
@ComponentScan(basePackages = {"pl.edu.wat.spz.web", "pl.edu.wat.spz.backend"})
@EnableJpaRepositories("pl.edu.wat.spz.backend.repository")
@EntityScan("pl.edu.wat.spz.domain.entity")
public class MvcConfig implements WebMvcConfigurer {

    private static final String POLISH_LOCALE_STRING = "pl";
    private static final String LANG_SWITCH_PARAM_NAME = "lang";
    private static final String LANG_COOKIE_NAME = "locale";

    @Autowired
    private PatientHandlerInterceptor patientHandlerInterceptor;

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString(POLISH_LOCALE_STRING));
        cookieLocaleResolver.setCookieName(LANG_COOKIE_NAME);
        return cookieLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(LANG_SWITCH_PARAM_NAME);
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(patientHandlerInterceptor)
                .addPathPatterns("/", "/patient/**").excludePathPatterns("/patient/details");

        registry.addInterceptor(localeChangeInterceptor());
    }
}


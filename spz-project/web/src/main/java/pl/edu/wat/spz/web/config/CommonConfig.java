package pl.edu.wat.spz.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wat.spz.commons.utils.Constans;

import java.text.SimpleDateFormat;

@Configuration
public class CommonConfig {

    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat(Constans.DATE_FORMAT);
    }
}

package pl.edu.wat.spz.backend.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VisitEmailTask {

    private static final long TEN_MINUTES = 10 * 60 * 1000L;

    @Scheduled(fixedRate = TEN_MINUTES)
    public void sendVisitEmailIfNecessary() {
    }

}

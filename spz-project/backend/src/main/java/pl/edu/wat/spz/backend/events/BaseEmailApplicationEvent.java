package pl.edu.wat.spz.backend.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseEmailApplicationEvent<T> extends ApplicationEvent {

    private HttpServletRequest request;

    public BaseEmailApplicationEvent(Object source) {
        super(source);
        this.request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}

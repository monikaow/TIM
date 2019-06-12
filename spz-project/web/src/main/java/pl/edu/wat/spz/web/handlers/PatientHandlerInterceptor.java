package pl.edu.wat.spz.web.handlers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pl.edu.wat.spz.domain.entity.Patient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PatientHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof Patient) {
            String nationalId = ((Patient) user).getNationalId();
            if (StringUtils.isEmpty(nationalId)) {
                response.sendRedirect("/patient/details");
                return false;
            }
        }

        return true;

    }
}

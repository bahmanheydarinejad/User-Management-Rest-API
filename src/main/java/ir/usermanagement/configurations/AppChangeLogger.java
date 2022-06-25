package ir.usermanagement.configurations;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AppChangeLogger {

    @Before("execution(* ir.usermanagement.models.repositories.*.save(..))")
    public void beforeChanged(JoinPoint point) {
        log.trace("Before calling ==> " + point.getSignature().toString());
    }

    @After("execution(* ir.usermanagement.models.repositories.*.save(..))")
    public void afterChanged(JoinPoint point) {
        log.trace("After calling ==> " + point.getSignature().toString());
    }
}

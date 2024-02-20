package pl.jakubmuczyn.aspect;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Moglibyśmy całą obsługę logiki biznesowej przenieść do aspektów, lecz nie jest
 * to zalecane, chociażby dlatego, że zwracamy Objects, które trzeba byłoby
 * rzutować na bardziej specyficzne klasy, czy przez inne niewygodne tematy.
 */

@Aspect // faktyczna obsługa aspektów
@Component // zarejestrowanie w Springu tej klasy
class LogicAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LogicAspect.class);
    private final Timer createGroupByProjectTimer;
    
    public LogicAspect(final MeterRegistry meterRegistry) { // podczas tworzenia tej klasy Sprign wstrzyknie rejestr metryk. MeterRegistry pozwala też tworzyć czasomierze
        createGroupByProjectTimer = meterRegistry.timer("logic.project.create.group");
    }
    
    @Before("execution(* pl.jakubmuczyn.logic.ProjectService.createGroup(..))")
    void logMethodCall(JoinPoint joinPoint) {
        logger.info("Before {} with {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }
    
    // @Around - aspekt
    // * - dowolny typ zwracany
    // .. - jakieś parametry
    @Around("execution(* pl.jakubmuczyn.logic.ProjectService.createGroup(..))")
    Object aroungCreateGroupByProject(ProceedingJoinPoint joinPoint) {
        return createGroupByProjectTimer.record(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                } else {
                    throw new RuntimeException(e); // ręczne owrapowanie wyjątku w RuntimeException
                }
            }
        });
    }
}

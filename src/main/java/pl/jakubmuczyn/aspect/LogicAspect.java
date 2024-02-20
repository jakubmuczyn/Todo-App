package pl.jakubmuczyn.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Moglibyśmy całą obsługę logiki biznesowej przenieść do aspektów, lecz nie jest
 * to zalecane, chociażby dlatego, że zwracamy Objects, które trzeba byłoby
 * rzutować na bardziej specyficzne klasy, czy przez inne niewygodne tematy.
 * */

@Aspect // faktyczna obsługa aspektów
@Component // zarejestrowanie w Springu tej klasy
class LogicAspect {
    
    @Around()
    Object aroungCreateGroupByProject(ProceedingJoinPoint joinPoint) {
        return joinPoint.proceed();
    }
}

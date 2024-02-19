package pl.jakubmuczyn.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // jakie elementy możemy adnotować (tu: klasy, interfejsy, enumy, itp)
@Retention(RetentionPolicy.RUNTIME) // jak długo ta adnotacja ma pozostawać (jest dostępna w runtimie)
@interface IllegalExceptionProcessing {
}

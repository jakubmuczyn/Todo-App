package pl.jakubmuczyn;

import jakarta.validation.Validator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
// @ComponentScan(basePackages = "db.migration") - skanowanie innych ścieżek
// @Import(TaskConfigurationProperties.class) - import innych klas konfiguracyjnych
public class TodoAppApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }
    
    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}

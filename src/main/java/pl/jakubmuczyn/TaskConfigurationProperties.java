package pl.jakubmuczyn;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {
    
    public Template template;
    
    @Getter
    @Setter
    public static class Template {
        private boolean allowMultipleTasks;
    }
}



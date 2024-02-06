package pl.jakubmuczyn;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("task")
public class TaskConfigurationProperties {
    private boolean allowMultipleTasksFromTemplate;
}

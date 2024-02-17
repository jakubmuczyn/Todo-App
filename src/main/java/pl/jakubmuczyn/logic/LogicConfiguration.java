package pl.jakubmuczyn.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jakubmuczyn.TaskConfigurationProperties;
import pl.jakubmuczyn.model.ProjectRepository;
import pl.jakubmuczyn.model.TaskGroupRepository;

@Configuration
class LogicConfiguration {
    
    @Bean
    ProjectService service(
            final ProjectRepository projectRepository,
            final TaskGroupRepository taskGroupRepository,
            final TaskConfigurationProperties taskConfigurationProperties
    ) {
        return new ProjectService(projectRepository, taskGroupRepository, taskConfigurationProperties);
    }
}

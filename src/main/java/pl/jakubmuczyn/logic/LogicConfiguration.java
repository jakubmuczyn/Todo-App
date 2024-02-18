package pl.jakubmuczyn.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jakubmuczyn.TaskConfigurationProperties;
import pl.jakubmuczyn.model.ProjectRepository;
import pl.jakubmuczyn.model.TaskGroupRepository;
import pl.jakubmuczyn.model.TaskRepository;

@Configuration
class LogicConfiguration {
    
    @Bean
    ProjectService projectService(
            final ProjectRepository projectRepository,
            final TaskGroupRepository taskGroupRepository,
            final TaskGroupService taskGroupService,
            final TaskConfigurationProperties taskConfigurationProperties
    ) {
        return new ProjectService(projectRepository, taskGroupRepository, taskGroupService, taskConfigurationProperties);
    }
    
    @Bean
    TaskGroupService taskGroupService(
            final TaskRepository taskRepository,
            final TaskGroupRepository taskGroupRepository
    ) {
        return new TaskGroupService(taskRepository, taskGroupRepository);
    }
}

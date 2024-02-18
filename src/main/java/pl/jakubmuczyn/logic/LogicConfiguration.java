package pl.jakubmuczyn.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jakubmuczyn.TaskConfigurationProperties;
import pl.jakubmuczyn.model.ProjectRepository;
import pl.jakubmuczyn.model.GroupRepository;
import pl.jakubmuczyn.model.TaskRepository;

@Configuration
class LogicConfiguration {
    
    @Bean
    ProjectService projectService(
            final ProjectRepository projectRepository,
            final GroupRepository groupRepository,
            final GroupService groupService,
            final TaskConfigurationProperties taskConfigurationProperties
    ) {
        return new ProjectService(projectRepository, groupRepository, groupService, taskConfigurationProperties);
    }
    
    @Bean
    GroupService groupService(
            final TaskRepository taskRepository,
            final GroupRepository groupRepository
    ) {
        return new GroupService(taskRepository, groupRepository);
    }
}

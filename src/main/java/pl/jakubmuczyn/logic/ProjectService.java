package pl.jakubmuczyn.logic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jakubmuczyn.TaskConfigurationProperties;
import pl.jakubmuczyn.model.*;
import pl.jakubmuczyn.model.projection.GroupReadModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProjectService {
    
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties configurationProperties;
    
    public List<Project> readAll() {
        return repository.findAll();
    }
    
    public Project save(Project toSave) {
        return repository.save(toSave);
    }
    
    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if (!configurationProperties.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed.");
        }
        TaskGroup result = repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new TaskGroup();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> new Task(
                                            projectStep.getDescription(),
                                            deadline.plusDays(projectStep.getDaysToDeadline()))
                                    ).collect(Collectors.toSet())
                    );
                    targetGroup.setProject(project);
                    return taskGroupRepository.save(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found."));
        return new GroupReadModel(result);
    }
}
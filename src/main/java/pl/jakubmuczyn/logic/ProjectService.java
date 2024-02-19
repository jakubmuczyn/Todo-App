package pl.jakubmuczyn.logic;

import lombok.AllArgsConstructor;
import pl.jakubmuczyn.TaskConfigurationProperties;
import pl.jakubmuczyn.model.Project;
import pl.jakubmuczyn.model.ProjectRepository;
import pl.jakubmuczyn.model.GroupRepository;
import pl.jakubmuczyn.model.projection.GroupReadModel;
import pl.jakubmuczyn.model.projection.ProjectWriteModel;
import pl.jakubmuczyn.model.projection.TaskWriteModel;
import pl.jakubmuczyn.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
// @Service
public class ProjectService {
    
    private ProjectRepository repository;
    private GroupRepository groupRepository;
    private GroupService groupService;
    private TaskConfigurationProperties configurationProperties;
    
    public List<Project> readAll() {
        return repository.findAll();
    }
    
    public Project save(final ProjectWriteModel toSave) {
        return repository.save(toSave.toProject());
    }
    
    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if (!configurationProperties.getTemplate().isAllowMultipleTasks() && groupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed.");
        }
        return repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> {
                                                var task = new TaskWriteModel();
                                                task.setDescription(projectStep.getDescription());
                                                task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                                return task;
                                            }
                                    ).collect(Collectors.toSet())
                    );
                    return groupService.createGroup(targetGroup, project);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found."));
    }
}
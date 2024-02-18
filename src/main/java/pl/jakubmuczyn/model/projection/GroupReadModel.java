package pl.jakubmuczyn.model.projection;

import lombok.Getter;
import lombok.Setter;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.Group;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class GroupReadModel {
    
    private int id;
    
    private String description;
    
    /**
     * Deadline from the latest task in group.
     */
    private LocalDateTime deadline;
    
    private Set<TaskReadModel> tasks;
    
    public GroupReadModel(Group source) {
        id = source.getId();
        description = source.getDescription();
        source.getTasks().stream()
                .map(Task::getDeadline)
                .max(LocalDateTime::compareTo)
                .ifPresent(date -> deadline = date);
        tasks = source.getTasks().stream()
                .map(TaskReadModel::new)
                .collect(Collectors.toSet());
    }
}

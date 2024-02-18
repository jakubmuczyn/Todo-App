package pl.jakubmuczyn.model.projection;

import lombok.Getter;
import lombok.Setter;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.Group;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskWriteModel {
    
    private String description;
    private LocalDateTime deadline;
    
    public Task toTask(final Group group) {
        return new Task(description, deadline, group);
    }
}

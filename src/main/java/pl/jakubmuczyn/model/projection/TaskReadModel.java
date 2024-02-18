package pl.jakubmuczyn.model.projection;

import lombok.Getter;
import lombok.Setter;
import pl.jakubmuczyn.model.Task;

@Getter
@Setter
public class TaskReadModel {
    
    private String description;
    private boolean done;
    
    public TaskReadModel(Task source) {
        description = source.getDescription();
        done = source.isDone();
    }
}
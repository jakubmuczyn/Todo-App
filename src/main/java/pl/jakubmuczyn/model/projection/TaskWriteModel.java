package pl.jakubmuczyn.model.projection;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.Group;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskWriteModel { // task w obrębie grupy
    
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;
    
    public Task toTask(final Group group) {
        return new Task(description, deadline, group);
    }
}

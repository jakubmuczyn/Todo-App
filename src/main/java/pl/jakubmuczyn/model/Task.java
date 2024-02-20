package pl.jakubmuczyn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import pl.jakubmuczyn.model.event.TaskEvent;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    private boolean done;
    private LocalDateTime deadline;
    @Embedded
    private Audit audit = new Audit();
    @ManyToOne // many tasks to one group
    @JoinColumn(name = "group_id")
    private Group group;
    
    public Task(String description, LocalDateTime deadline) {
        this(description, deadline, null);
    }
    
    public Task(String description, LocalDateTime deadline, Group group) {
        this.description = description;
        this.deadline = deadline;
        if (group != null) {
            this.group = group;
        }
    }
    
    public int getId() {
        return id;
    }
    
    void setId(final int id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    void setDescription(final String description) {
        this.description = description;
    }
    
    public boolean isDone() {
        return done;
    }
    
    public TaskEvent toggle() {
        this.done = !this.done;
        return TaskEvent.changed(this);
    }
    
    public LocalDateTime getDeadline() {
        return deadline;
    }
    
    void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }
    
    Group getGroup() {
        return group;
    }
    
    void setGroup(final Group group) {
        this.group = group;
    }
    
    public void updateFrom(final Task source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group = source.group;
    }
}

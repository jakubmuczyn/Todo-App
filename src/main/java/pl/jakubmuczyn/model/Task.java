package pl.jakubmuczyn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;
    
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
    
    public void setDone(final boolean done) {
        this.done = done;
    }
    
    public LocalDateTime getDeadline() {
        return deadline;
    }
    
    void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }
    
    TaskGroup getGroup() {
        return group;
    }
    
    void setGroup(final TaskGroup group) {
        this.group = group;
    }
    
    public void updateFrom(final Task source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group = source.group;
    }
}

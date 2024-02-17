package pl.jakubmuczyn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "task_groups")
public class TaskGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank(message = "Task group's description must not be empty")
    private String description;
    
    private boolean done;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group") // jedna grupa przypisana do wielu tasków
    private Set<Task> tasks;
    
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    
    public int getId() {
        return id;
    }
    
    void setId(final int id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public boolean isDone() {
        return done;
    }
    
    public void setDone(final boolean done) {
        this.done = done;
    }
    
    public Set<Task> getTasks() {
        return tasks;
    }
    
    public void setTasks(final Set<Task> tasks) {
        this.tasks = tasks;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(final Project project) {
        this.project = project;
    }
}

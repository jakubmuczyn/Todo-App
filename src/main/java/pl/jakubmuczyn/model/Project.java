package pl.jakubmuczyn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank(message = "Project's description must not be empty")
    private String description;
    
    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> groups;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectStep> steps;
    
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
    
    Set<TaskGroup> getGroups() {
        return groups;
    }
    
    void setGroups(final Set<TaskGroup> groups) {
        this.groups = groups;
    }
    
    public Set<ProjectStep> getSteps() {
        return steps;
    }
    
    void setSteps(final Set<ProjectStep> steps) {
        this.steps = steps;
    }
}

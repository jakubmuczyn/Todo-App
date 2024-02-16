package pl.jakubmuczyn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "task_groups")
public class TaskGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PACKAGE) // setter != public
    private int id;
    
    @NotBlank(message = "Task group's description must not be empty")
    @Setter(AccessLevel.PACKAGE)
    private String description;
    
    private boolean done;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group") // jedna grupa przypisana do wielu tasków
    @Setter(AccessLevel.PACKAGE)
    private Set<Task> tasks;
}

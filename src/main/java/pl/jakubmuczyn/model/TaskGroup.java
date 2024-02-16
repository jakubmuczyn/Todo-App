package pl.jakubmuczyn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    
    @Embedded
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Audit audit = new Audit();
}

package pl.jakubmuczyn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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
    
    @Transient // tego pola nie chcemy zapisywać w bazie danych
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdOn;
    
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedOn;
}

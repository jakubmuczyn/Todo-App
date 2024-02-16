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
    @Setter(AccessLevel.PACKAGE) // setter != public
    private int id;
    
    @NotBlank(message = "Task's description must not be empty")
    @Setter(AccessLevel.PACKAGE)
    private String description;
    
    private boolean done;
    
    @Setter(AccessLevel.PACKAGE)
    private LocalDateTime deadline;
    
    // @Transient - tego pola nie chcemy zapisywać w bazie danych
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdOn;
    
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedOn;
    
    public void updateFrom(final Task source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
    }
    
    @PrePersist // uruchomi się tuż przed zapisem do bazy danych; operacja do insertu na bazie danych
    void prePersist() {
        createdOn = LocalDateTime.now();
    }
    
    @PreUpdate // uruchomi się tuż przed updatem na bazie danych
    void preMerge() {
        updatedOn = LocalDateTime.now();
    }
}

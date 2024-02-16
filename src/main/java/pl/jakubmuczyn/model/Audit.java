package pl.jakubmuczyn.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@Embeddable
class Audit {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    
    @PrePersist // uruchomi się tuż przed zapisem do bazy danych; operacja do insertu na bazie danych
    void prePersist() {
        createdOn = LocalDateTime.now();
    }
    
    @PreUpdate // uruchomi się tuż przed updatem na bazie danych
    void preMerge() {
        updatedOn = LocalDateTime.now();
    }
}

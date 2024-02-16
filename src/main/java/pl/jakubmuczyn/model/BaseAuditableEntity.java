package pl.jakubmuczyn.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass // klasa bazowa która nie ma odzwierciedlenia w tabeli
abstract class BaseAuditableEntity {
    
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

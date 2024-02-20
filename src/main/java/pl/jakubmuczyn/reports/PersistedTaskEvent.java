package pl.jakubmuczyn.reports;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import pl.jakubmuczyn.model.event.TaskEvent;

import java.time.LocalDateTime;
import java.time.ZoneId;

@NoArgsConstructor // ponieważ mamy do czynienia z JPA
@Entity
@Table(name = "task_events")
class PersistedTaskEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int taskId;
    String name;
    LocalDateTime occurrence;
    
    public PersistedTaskEvent(TaskEvent taskEvent) {
        taskId = taskEvent.getTaskId();
        name = taskEvent.getClass().getSimpleName();
        occurrence = LocalDateTime.ofInstant(taskEvent.getOccurrence(), ZoneId.systemDefault());
    }
    
    String getName() {
        return name;
    }
    
    LocalDateTime getOccurrence() {
        return occurrence;
    }
}
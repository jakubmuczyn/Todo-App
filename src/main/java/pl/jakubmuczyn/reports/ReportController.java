package pl.jakubmuczyn.reports;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/reports")
class ReportController {
    
    public static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final TaskRepository taskRepository;
    private final PersistedTaskEventRepository persistedTaskEventRepository;
    
    @GetMapping("/count/{id}")
    ResponseEntity<TaskWithChangesCount> readTaskWithCount(@PathVariable int id) {
        logger.info("Checking task counts");
        return taskRepository.findById(id)
                .map(task -> new TaskWithChangesCount(task, persistedTaskEventRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/donebeforedeadline/{id}")
    ResponseEntity<TaskDoneBeforeDeadlineOrNot> readTaskDoneBeforeDeadline(@PathVariable int id) {
        logger.info("Checking if the task has been completed before deadline");
        return taskRepository.findById(id)
                .map(task -> new TaskDoneBeforeDeadlineOrNot(task, persistedTaskEventRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    private static class TaskWithChangesCount {
        public String description;
        public boolean done;
        public int changesCount;
        
        TaskWithChangesCount(final Task task, final List<PersistedTaskEvent> events) {
            description = task.getDescription();
            done = task.isDone();
            changesCount = events.size();
        }
    }
    
    private static class TaskDoneBeforeDeadlineOrNot {
        
        public String description;
        public boolean done;
        public LocalDateTime occurrence;
        public LocalDateTime deadline;
        public String doneBeforeDeadline;
        
        TaskDoneBeforeDeadlineOrNot(final Task task, final List<PersistedTaskEvent> events) {
            description = task.getDescription();
            done = task.isDone();
            occurrence = events.isEmpty() ? null : findLatestEventOccurrence(events);
            deadline = task.getDeadline();
            doneBeforeDeadline = isDoneBeforeDeadline(task);
        }
        
        private String isDoneBeforeDeadline(final Task task) {
            if (!task.isDone()) {
                return "no"; // task nie jest ukończony
            } else {
                if (deadline == null) {
                    return "yes"; // task nie miał deadline'a, a więc mimo wszystko spełnia warunek
                } else if (occurrence == null) {
                    return "yes"; // jest ukończony, ale nie było żadnych zmian, więc został tak dodany
                } else {
                    return occurrence.isBefore(deadline) ? "yes" : "no";
                }
            }
        }
        
        private LocalDateTime findLatestEventOccurrence(final List<PersistedTaskEvent> events) {
            LocalDateTime latestOccurrence = null;
            for (PersistedTaskEvent event : events) {
                if (isTypeOfDoneOrUndone(event)
                        && event.getOccurrence() != null
                        && (latestOccurrence == null || event.getOccurrence().isAfter(latestOccurrence)))
                    latestOccurrence = event.getOccurrence();
            }
            return latestOccurrence;
        }
        
        private static boolean isTypeOfDoneOrUndone(final PersistedTaskEvent event) {
            return event.getName().equals("TaskDone") || event.getName().equals("TaskUndone");
        }
    }
}

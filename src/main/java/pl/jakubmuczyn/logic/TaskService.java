package pl.jakubmuczyn.logic;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.TaskRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    @Async
    public CompletableFuture<List<Task>> findAllAsync() {
        return CompletableFuture.supplyAsync(taskRepository::findAll);
    }
}

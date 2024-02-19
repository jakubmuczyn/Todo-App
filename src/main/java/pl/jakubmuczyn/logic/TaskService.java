package pl.jakubmuczyn.logic;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.TaskRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class TaskService {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    
    private final TaskRepository taskRepository;
    
    @Async
    public CompletableFuture<List<Task>> findAllAsync() {
        logger.info("Supply async!");
        return CompletableFuture.supplyAsync(taskRepository::findAll);
    }
}

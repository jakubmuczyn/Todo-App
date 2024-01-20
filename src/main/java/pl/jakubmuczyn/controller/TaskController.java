package pl.jakubmuczyn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import pl.jakubmuczyn.model.TaskRepository;

@RepositoryRestController
class TaskController {
    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;
    
    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping("/tasks")
    ResponseEntity<?> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }
}

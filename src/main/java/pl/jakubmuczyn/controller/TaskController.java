package pl.jakubmuczyn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import pl.jakubmuczyn.model.TaskRepository;

@RepositoryRestController
class TaskController {
    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;
    
    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }
    
    
}

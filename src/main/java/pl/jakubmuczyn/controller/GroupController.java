package pl.jakubmuczyn.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.jakubmuczyn.logic.GroupService;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.TaskRepository;
import pl.jakubmuczyn.model.projection.GroupReadModel;
import pl.jakubmuczyn.model.projection.GroupWriteModel;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
class GroupController {
    
    public static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    private final GroupService groupService;
    private final TaskRepository taskRepository;
    
    GroupController(final GroupService groupService, final TaskRepository taskRepository) {
        this.groupService = groupService;
        this.taskRepository = taskRepository;
    }
    
    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel groupWriteModel) {
        GroupReadModel result = groupService.createGroup(groupWriteModel);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    
    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        logger.warn("Exposing all the groups!");
        return ResponseEntity.ok(groupService.readAll());
    }
    
    @GetMapping("/{id}")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(taskRepository.findAllByGroup_Id(id));
    }
    
    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        groupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
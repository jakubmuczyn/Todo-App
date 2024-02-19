package pl.jakubmuczyn.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.jakubmuczyn.logic.GroupService;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.TaskRepository;
import pl.jakubmuczyn.model.projection.GroupReadModel;
import pl.jakubmuczyn.model.projection.GroupWriteModel;
import pl.jakubmuczyn.model.projection.TaskWriteModel;

import java.net.URI;
import java.util.List;

@Controller
@IllegalExceptionProcessing
@RequestMapping("/groups")
class GroupController {
    
    public static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    private final GroupService groupService;
    private final TaskRepository taskRepository;
    
    GroupController(final GroupService groupService, final TaskRepository taskRepository) {
        this.groupService = groupService;
        this.taskRepository = taskRepository;
    }
    
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model) {
        model.addAttribute("group", new GroupWriteModel());
        return "groups";
    }
    
    @PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroup(
            @ModelAttribute("group") @Valid GroupWriteModel groupWriteModel,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "groups";
        }
        groupService.createGroup(groupWriteModel);
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", getGroups());
        model.addAttribute("message", "Dodano grupę!");
        return "groups";
    }
    
    @PostMapping(params = "addTask", produces = MediaType.TEXT_HTML_VALUE) // metody odpalają się tylko gdy działamy przez Thymeleafa lub inny html
    String addGroupTask(@ModelAttribute("group") GroupWriteModel groupWriteModel) {
        groupWriteModel.getTasks().add(new TaskWriteModel());
        return "groups";
    }
    
    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel groupWriteModel) {
        GroupReadModel result = groupService.createGroup(groupWriteModel);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        logger.warn("Exposing all the groups!");
        return ResponseEntity.ok(groupService.readAll());
    }
    
    @ResponseBody
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(taskRepository.findAllByGroup_Id(id));
    }
    
    @ResponseBody
    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        groupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }
    
    @ModelAttribute("groups")
    List<GroupReadModel> getGroups() {
        return groupService.readAll();
    }
}
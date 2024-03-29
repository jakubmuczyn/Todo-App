package pl.jakubmuczyn.controller;

import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.jakubmuczyn.logic.ProjectService;
import pl.jakubmuczyn.model.Project;
import pl.jakubmuczyn.model.ProjectStep;
import pl.jakubmuczyn.model.projection.ProjectWriteModel;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/projects")
class ProjectController {
    
    private final ProjectService projectService;
    
    @GetMapping
    String showProjects(Model model) {
        model.addAttribute("project", new ProjectWriteModel());
        return "projects";
    }
    
    @PostMapping
    String addProject(
            @ModelAttribute("project") @Valid ProjectWriteModel projectWriteModel,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "projects";
        }
        projectService.save(projectWriteModel);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("projects", getProjects());
        model.addAttribute("message", "Dodano projekt!");
        return "projects";
    }
    
    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel projectWriteModel) {
        projectWriteModel.getSteps().add(new ProjectStep());
        return "projects";
    }
    
    @PostMapping(params = "removeStep")
    String removeProjectStep(@ModelAttribute("project") ProjectWriteModel projectWriteModel) {
        List<ProjectStep> steps = projectWriteModel.getSteps();
        if (!steps.isEmpty()) {
            steps.remove(steps.size() - 1);
        }
        return "projects";
    }
    
    // tworzymy grupę wywołując ją przez metodę, bez Springowego proxy, więc @Timed nie zadziała
    @PostMapping("/fake/{id}")
    String createFakeGroup(
            @ModelAttribute("project") ProjectWriteModel projectWriteModel,
            Model model,
            @PathVariable int id,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline
    ) {
        return createGroup(projectWriteModel, model, id, deadline);
    }
    
    // @Timed - metoda wysyła do micrometera dodatkowe metryki, które możemy podejrzeć actuatorem
    @Timed(value = "project.create.group", histogram = true, percentiles = {0.5, 0.95, 0.99}) // histogram - zachowana ciągłość metryk
    @PostMapping("/{id}")
    String createGroup(
            @ModelAttribute("project") ProjectWriteModel projectWriteModel,
            Model model,
            @PathVariable int id,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline
    ) {
        try {
            projectService.createGroup(deadline, id);
            model.addAttribute("message", "Dodano grupę!");
        } catch (IllegalStateException | IllegalArgumentException e) {
            model.addAttribute("message", "Błąd podczas tworzenia grupy!");
        }
        return "projects";
    }
    
    @ModelAttribute("projects")
    List<Project> getProjects() {
        return projectService.readAll();
    }
}

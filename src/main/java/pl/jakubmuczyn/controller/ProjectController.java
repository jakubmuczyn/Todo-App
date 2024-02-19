package pl.jakubmuczyn.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jakubmuczyn.logic.ProjectService;
import pl.jakubmuczyn.model.Project;
import pl.jakubmuczyn.model.ProjectStep;
import pl.jakubmuczyn.model.projection.ProjectWriteModel;

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
    
    @ModelAttribute("projects")
    List<Project> getProjects() {
        return projectService.readAll();
    }
}

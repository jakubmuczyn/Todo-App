package pl.jakubmuczyn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jakubmuczyn.model.ProjectStep;
import pl.jakubmuczyn.model.projection.ProjectWriteModel;

@Controller
@RequestMapping("/projects")
class ProjectController {
    
    @GetMapping
    String showProjects(Model model) {
        model.addAttribute("project", new ProjectWriteModel());
        return "projects";
    }
    
    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel projectWriteModel) {
        projectWriteModel.getSteps().add(new ProjectStep());
        return "projects";
    }
}

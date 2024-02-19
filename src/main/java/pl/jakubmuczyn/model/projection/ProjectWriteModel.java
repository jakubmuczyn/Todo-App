package pl.jakubmuczyn.model.projection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import pl.jakubmuczyn.model.Project;
import pl.jakubmuczyn.model.ProjectStep;

import java.util.HashSet;
import java.util.List;

@Getter
@Setter
public class ProjectWriteModel {
    
    @NotBlank(message = "Project's description must not be empty")
    private String description;
    
    @Valid // walidowanie wgłąb
    private List<ProjectStep> steps;
    
    public Project toProject() {
        var result = new Project();
        result.setDescription(description);
        steps.forEach(step -> step.setProject(result));
        result.setSteps(new HashSet<>(steps));
        return result;
    }
}

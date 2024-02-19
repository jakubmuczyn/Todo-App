package pl.jakubmuczyn.model.projection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import pl.jakubmuczyn.model.Group;
import pl.jakubmuczyn.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GroupWriteModel {
    
    @NotBlank(message = "Group's description must not be empty")
    private String description;
    
    @Valid
    private List<TaskWriteModel> tasks = new ArrayList<>();
    
    public GroupWriteModel() {
        tasks.add(new TaskWriteModel()); // aby był pusty task który szablon wyrenderuje jako pola formularza
    }
    
    public Group toGroup(final Project project) {
        var result = new Group();
        result.setDescription(description);
        result.setTasks(
                tasks.stream()
                        .map(source -> source.toTask(result))
                        .collect(Collectors.toSet())
        );
        result.setProject(project);
        return result;
    }
}

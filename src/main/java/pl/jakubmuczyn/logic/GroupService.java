package pl.jakubmuczyn.logic;

import lombok.AllArgsConstructor;
import org.springframework.web.context.annotation.RequestScope;
import pl.jakubmuczyn.model.Group;
import pl.jakubmuczyn.model.GroupRepository;
import pl.jakubmuczyn.model.TaskRepository;
import pl.jakubmuczyn.model.projection.GroupReadModel;
import pl.jakubmuczyn.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
// @Service
@RequestScope // w obrębie jednego żądania mamy unikalną instancję serwisu (@Scope - adnotacja mówiąca jak ten obiekt powinien być wstrzykiwany)
public class GroupService {
    
    private TaskRepository taskRepository;
    private GroupRepository groupRepository;
    
    public GroupReadModel createGroup(final GroupWriteModel source) {
        Group result = groupRepository.save(source.toGroup());
        return new GroupReadModel(result);
    }
    
    public List<GroupReadModel> readAll() {
        return groupRepository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }
    
    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks. Do all the tasks first.");
        }
        Group result = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group with given id not found."));
        result.setDone(!result.isDone());
        groupRepository.save(result);
    }
}

package pl.jakubmuczyn.logic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.jakubmuczyn.model.TaskGroup;
import pl.jakubmuczyn.model.TaskGroupRepository;
import pl.jakubmuczyn.model.TaskRepository;
import pl.jakubmuczyn.model.projection.GroupReadModel;
import pl.jakubmuczyn.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@RequestScope // w obrębie jednego żądania mamy unikalną instancję serwisu (@Scope - adnotacja mówiąca jak ten obiekt powinien być wstrzykiwany)
public class TaskGroupService {
    
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;
    
    public GroupReadModel createGroup(final GroupWriteModel source) {
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }
    
    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }
    
    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks. Do all the tasks first.");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found."));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}

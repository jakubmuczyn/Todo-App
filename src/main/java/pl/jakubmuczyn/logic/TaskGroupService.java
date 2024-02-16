package pl.jakubmuczyn.logic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jakubmuczyn.model.TaskGroup;
import pl.jakubmuczyn.model.TaskGroupRepository;
import pl.jakubmuczyn.model.projection.GroupReadModel;
import pl.jakubmuczyn.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TaskGroupService {
    
    private TaskGroupRepository repository;
    
    public GroupReadModel createGroup(GroupWriteModel source) {
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }
    
    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }
}

package pl.jakubmuczyn.model;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    List<Group> findAll();
    
    Optional<Group> findById(Integer id);
    
    Group save(Group entity);
    
    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);
}

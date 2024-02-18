package pl.jakubmuczyn.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from TASKS where ID=:id")
    boolean existsById(@Param("id") Integer id);
    
    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);
    
    @Override
    List<Task> findAllByGroup_Id(Integer groupId);
    
    @Override
    List<Task> findAllByDeadlineIsNullOrDeadlineBefore(LocalDateTime today);
}

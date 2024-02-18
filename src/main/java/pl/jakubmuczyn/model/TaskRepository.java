package pl.jakubmuczyn.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    Page<Task> findAll(Pageable page);
    Optional<Task> findById(Integer id);
    boolean existsById(Integer id);
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);
    Task save(Task entity);
    List<Task> findByDone(boolean done);
    List<Task> findAllByGroup_Id(Integer groupId);
    List<Task> findAllByDeadlineIsNullOrDeadlineBefore(LocalDateTime today);
}

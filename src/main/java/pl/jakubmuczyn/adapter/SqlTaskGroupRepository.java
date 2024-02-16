package pl.jakubmuczyn.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jakubmuczyn.model.TaskGroup;
import pl.jakubmuczyn.model.TaskGroupRepository;

@Repository
interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {
}

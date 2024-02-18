package pl.jakubmuczyn.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jakubmuczyn.model.Group;
import pl.jakubmuczyn.model.GroupRepository;

import java.util.List;

@Repository
interface SqlGroupRepository extends GroupRepository, JpaRepository<Group, Integer> {
    @Override
    @Query("select distinct g from Group g join fetch g.tasks") // distinct zwraca unikalne wyniki, fetch dociąga od razu wszystkie taski rozwiązując problem n+1 selectów
    List<Group> findAll();
    
    @Override
    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);
}

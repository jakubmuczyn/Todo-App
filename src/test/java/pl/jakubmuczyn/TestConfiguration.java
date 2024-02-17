package pl.jakubmuczyn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.TaskRepository;

import java.util.*;

@Configuration
class TestConfiguration {
    
    @Bean
    @Primary
    @Profile("integration")
    TaskRepository testTaskRepository() { // repozytorium na potrzeby testów
        return new TaskRepository() {
            private Map<Integer, Task> tasks = new HashMap<>();
            
            @Override
            public List<Task> findAll () {
                return new ArrayList<>(tasks.values());
            }
            
            @Override
            public Page<Task> findAll ( final Pageable page){
                return null;
            }
            
            @Override
            public Optional<Task> findById ( final Integer id){
                return Optional.ofNullable(tasks.get(id));
            }
            
            @Override
            public boolean existsById ( final Integer id){
                return tasks.containsKey(id);
            }
            
            @Override
            public boolean existsByDoneIsFalseAndGroup_Id ( final Integer groupId){
                return false;
            }
            
            @Override
            public Task save ( final Task entity){
                return tasks.put(tasks.size() + 1, entity);
            }
            
            @Override
            public List<Task> findByDone ( final boolean done){
                return null;
            }
        };
    }
}
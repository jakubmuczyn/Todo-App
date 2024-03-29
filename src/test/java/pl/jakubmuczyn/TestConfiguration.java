package pl.jakubmuczyn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.TaskRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
class TestConfiguration {
    
    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2eTestDataSource() {
        var result = new DriverManagerDataSource("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "jakubmuczyn", "");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }
    
    @Bean
    @Primary
    @Profile("integration")
    TaskRepository testTaskRepository() { // repozytorium na potrzeby testów
        return new TaskRepository() {
            private Map<Integer, Task> tasks = new HashMap<>();
            
            @Override
            public List<Task> findAll() {
                return new ArrayList<>(tasks.values());
            }
            
            @Override
            public Page<Task> findAll(final Pageable page) {
                return null;
            }
            
            @Override
            public Optional<Task> findById(final Integer id) {
                return Optional.ofNullable(tasks.get(id));
            }
            
            @Override
            public boolean existsById(final Integer id) {
                return tasks.containsKey(id);
            }
            
            @Override
            public boolean existsByDoneIsFalseAndGroup_Id(final Integer groupId) {
                return false;
            }
            
            @Override
            public Task save(final Task entity) {
                final int key = tasks.size() + 1;
                try {
                    var field = Task.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity, key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                tasks.put(key, entity);
                return tasks.get(key);
            }
            
            @Override
            public List<Task> findByDone(final boolean done) {
                return null;
            }
            
            @Override
            public List<Task> findAllByGroup_Id(final Integer groupId) {
                return List.of();
            }
            
            @Override
            public List<Task> findAllByDeadlineIsNullOrDeadlineBefore(final LocalDateTime today) {
                return List.of();
            }
        };
    }
}
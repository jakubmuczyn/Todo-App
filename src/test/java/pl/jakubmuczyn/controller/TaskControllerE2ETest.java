package pl.jakubmuczyn.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.TaskRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate testRestTemplate; // możemy strzelać zapytaniami jak w Postmanie
    
    @Autowired
    TaskRepository taskRepository;

    @Test
    void httpGet_returnsAllTasks() {
        // given
        int initial = taskRepository.findAll().size();
        taskRepository.save(new Task("test description 1", LocalDateTime.now()));
        taskRepository.save(new Task("test description 2", LocalDateTime.now()));
        
        // when
        Task[] result = testRestTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);
        
        // then
        assertThat(result).hasSize(initial + 2);
    }
    
    @Test
    void httpGet_returnsGivenTask() {
        // given
        Task task = taskRepository.save(new Task("test description 1", LocalDateTime.now()));
        
        // when
        Task result = testRestTemplate.getForObject("http://localhost:" + port + "/tasks/" + task.getId(), Task.class);
        
        // then
        assertThat(result.getDescription()).isEqualTo(task.getDescription());
    }
}
package pl.jakubmuczyn.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import pl.jakubmuczyn.model.Task;
import pl.jakubmuczyn.model.TaskRepository;

import java.time.LocalDateTime;

@SpringBootTest
@Profile("integration")
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private TaskRepository taskRepository; // podmienione repozytorium na testowe
    
    @Test
    void httpGet_returnsGivenTask() {
        // given
        taskRepository.save(new Task("test description 1", LocalDateTime.now()));
        
        // expect (when + then)
        mockMvc.perform()
        
        
    }
}

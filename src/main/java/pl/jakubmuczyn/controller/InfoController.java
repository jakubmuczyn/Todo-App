package pl.jakubmuczyn.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubmuczyn.TaskConfigurationProperties;

@AllArgsConstructor
@RequestMapping("/info")
@RestController
public class InfoController {
    
    private DataSourceProperties dataSource;
    private TaskConfigurationProperties myProp;
    
    @GetMapping("/url")
    String url() {
        return dataSource.getUrl();
    }
    
    @GetMapping("/prop")
    boolean myProp() {
        return myProp.getTemplate().isAllowMultipleTasks();
    }
}

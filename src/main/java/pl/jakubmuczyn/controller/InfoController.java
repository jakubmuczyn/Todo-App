package pl.jakubmuczyn.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubmuczyn.TaskConfigurationProperties;

@AllArgsConstructor
@RestController
public class InfoController {
    
    private DataSourceProperties dataSource;
    private TaskConfigurationProperties myProp;
    
    @GetMapping("/info/url")
    String url() {
        return dataSource.getUrl();
    }
    
    @GetMapping("/info/prop")
    boolean myProp() {
        return myProp.getTemplate().isAllowMultipleTasks();
    }
}

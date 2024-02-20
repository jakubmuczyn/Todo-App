package pl.jakubmuczyn;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.jakubmuczyn.model.Group;
import pl.jakubmuczyn.model.GroupRepository;
import pl.jakubmuczyn.model.Task;

import java.util.Set;

@AllArgsConstructor
@Component
class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    
    private static final Logger logger = LoggerFactory.getLogger(Warmup.class);
    private final GroupRepository groupRepository;
    
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        logger.info("Application warmup after context refreshed");
        final String description = "ApplicationContextEvent"; // ContextRefreshedEvent extends ApplicationContextEvent
        if (!groupRepository.existsByDescription(description)) {
            logger.info("No required group found! Adding it!");
            var group = new Group();
            group.setDescription(description);
            group.setTasks(Set.of(
                    new Task("ContextClosedEvent", null, group),
                    new Task("ContextRefreshedEvent", null, group),
                    new Task("ContextStoppedEvent", null, group),
                    new Task("ContextStartedEvent", null, group)
            ));
            groupRepository.save(group);
        }
    }
}
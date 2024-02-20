package pl.jakubmuczyn.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.jakubmuczyn.model.event.TaskDone;
import pl.jakubmuczyn.model.event.TaskUndone;

@Service // service a nie component, bo jest to rzecz bardziej związana z procesowaniem/usługowością
class ChangedTaskEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);
    
    @EventListener
    public void on(TaskDone event) {
        logger.info("Got " + event);
    }
    
    @EventListener
    public void on(TaskUndone event) {
        logger.info("Got " + event);
    }
    
}

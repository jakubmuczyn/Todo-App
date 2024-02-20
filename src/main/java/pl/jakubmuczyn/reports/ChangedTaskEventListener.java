package pl.jakubmuczyn.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.jakubmuczyn.model.event.TaskDone;
import pl.jakubmuczyn.model.event.TaskEvent;
import pl.jakubmuczyn.model.event.TaskUndone;

@Service // service a nie component, bo jest to rzecz bardziej związana z procesowaniem/usługowością
class ChangedTaskEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);
    private final PersistedTaskEventRepository persistedTaskEventRepository;
    
    ChangedTaskEventListener(final PersistedTaskEventRepository persistedTaskEventRepository) {
        this.persistedTaskEventRepository = persistedTaskEventRepository;
    }
    
    @Async
    @EventListener
    public void on(TaskDone event) {
        onChanged(event);
    }
    
    @Async
    @EventListener
    public void on(TaskUndone event) {
        onChanged(event);
    }
    
    private void onChanged(final TaskEvent event) {
        logger.info("Got " + event);
        persistedTaskEventRepository.save(new PersistedTaskEvent(event));
    }
    
}

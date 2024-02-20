package pl.jakubmuczyn.model.event;

import pl.jakubmuczyn.model.Task;

import java.time.Clock;

public class TaskUndone extends TaskEvent {
    TaskUndone(final Task task) {
        super(task.getId(), Clock.systemDefaultZone());
    }
}

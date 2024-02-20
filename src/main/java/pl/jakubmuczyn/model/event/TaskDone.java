package pl.jakubmuczyn.model.event;

import pl.jakubmuczyn.model.Task;

import java.time.Clock;

public class TaskDone extends TaskEvent {
    TaskDone(final Task task) {
        super(task.getId(), Clock.systemDefaultZone());
    }
}

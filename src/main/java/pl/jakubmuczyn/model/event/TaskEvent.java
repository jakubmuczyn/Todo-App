package pl.jakubmuczyn.model.event;

import lombok.Getter;
import pl.jakubmuczyn.model.Task;

import java.time.Clock;
import java.time.Instant;

/**
 * Klasa abstrakcyjna w Javie to klasa, która może zawierać metody abstrakcyjne,
 * czyli te, które nie posiadają implementacji. Nie można tworzyć instancji klas
 * abstrakcyjnych; służą one jako szablony dla klas dziedziczących, które muszą
 * zaimplementować ich abstrakcyjne metody.
 * *
 * klasa abstrakcyjna to tylko "baza", konkretne
 * przypadki będą niosły za sobą trochę więcej informacji.
 * */

@Getter // brak setterów = klasa niemutowalna
public abstract class TaskEvent {
    
    public static TaskEvent changed(Task task) {
        return task.isDone() ? new TaskDone(task) : new TaskUndone(task);
    }
    
    private int taskId;
    private Instant occurrence; // punkt w czasie
    
    TaskEvent(final int taskId, Clock clock) {
        this.taskId = taskId;
        this.occurrence = Instant.now(clock);
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "taskId=" + taskId +
                ", occurrence=" + occurrence +
                '}';
    }
}

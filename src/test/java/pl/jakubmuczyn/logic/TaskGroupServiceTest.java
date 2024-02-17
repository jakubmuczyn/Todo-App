package pl.jakubmuczyn.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.jakubmuczyn.model.TaskGroup;
import pl.jakubmuczyn.model.TaskGroupRepository;
import pl.jakubmuczyn.model.TaskRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {
    
    // metoda_stan_wynik()
    @Test
    @DisplayName("should throw IllegalStateException when group has undone tasks.")
    void toggleGroup_undoneTasks_throwsIllegalStateException() {
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);
        
        // system under test
        var taskGroupServiceToTest = new TaskGroupService(mockTaskRepository, null);
        
        // when
        var exception = catchThrowable(() -> taskGroupServiceToTest.toggleGroup(1));
        
        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("has undone tasks");
    }
    
    @Test
    @DisplayName("should throw IllegalArgumentException when no group.")
    void toggleGroup_wrongId_throwsIllegalArgumentException() {
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        // and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        // system under test
        var taskGroupServiceToTest = new TaskGroupService(mockTaskRepository, mockTaskGroupRepository);
        
        // when
        var exception = catchThrowable(() -> taskGroupServiceToTest.toggleGroup(1));
        
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }
    
    @Test
    @DisplayName("should toggle group.")
    void toggleGroup_worksAsExpected() {
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        // and
        var taskGroup = new TaskGroup();
        var beforeToggle = taskGroup.isDone();
        
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(taskGroup));
        
        // system under test
        var taskGroupServiceToTest = new TaskGroupService(mockTaskRepository, mockTaskGroupRepository);
        
        // when
        taskGroupServiceToTest.toggleGroup(0);
        
        // then
        assertThat(taskGroup.isDone()).isEqualTo(!beforeToggle);
    }
    
    private static TaskRepository taskRepositoryReturning(final boolean value) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(value);
        return mockTaskRepository;
    }
}
package pl.jakubmuczyn.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.jakubmuczyn.TaskConfigurationProperties;
import pl.jakubmuczyn.model.TaskGroupRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {
    
    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and the other undone group exists.")
    void createGroup_noMultipleGroupsConfig_and_undoneGroupExtsts_throwsIllegalStateException() {
        // given
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);
        
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(false);
        
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        
        // system under test
        var projectServiceToTest = new ProjectService(null, mockGroupRepository, mockConfig);
        
        // when
        var exception = catchThrowable(() -> projectServiceToTest.createGroup(LocalDateTime.now(), 0));
        
        // then
        assertThat(exception)
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("one undone group");
    }
}
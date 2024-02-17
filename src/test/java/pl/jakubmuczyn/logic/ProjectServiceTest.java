package pl.jakubmuczyn.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.jakubmuczyn.TaskConfigurationProperties;
import pl.jakubmuczyn.model.ProjectRepository;
import pl.jakubmuczyn.model.TaskGroup;
import pl.jakubmuczyn.model.TaskGroupRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {
    
    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and the other undone group exists.")
    void createGroup_noMultipleGroupsConfig_and_undoneGroupExtsts_throwsIllegalStateException() {
        // given
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(true);
        
        TaskConfigurationProperties mockConfig = configurationReturning(false);
        
        // system under test
        var projectServiceToTest = new ProjectService(null, mockGroupRepository, mockConfig);
        
        // when
        var exception = catchThrowable(() -> projectServiceToTest.createGroup(LocalDateTime.now(), 0));
        
        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");
    }
    
    @Test
    @DisplayName("should throw IllegalArgumentException when configuration is ok and no projects for given id.")
    void createGroup_configurationOk_and_noProjects_throwsIllegalArgumentException() {
        // given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        
        // system under test
        var projectServiceToTest = new ProjectService(mockRepository, null, mockConfig);
        
        // when
        var exception = catchThrowable(() -> projectServiceToTest.createGroup(LocalDateTime.now(), 0));
        
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }
    
    @Test
    @DisplayName("should throw IllegalArgumentException when configured to allow just 1 group and no groups and projects for given id.")
    void createGroup_noMultipleGroupsConfig_and_noUndoneGroupExtsts_noProjects_throwsIllegalArgumentException() {
        // given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        
        // system under test
        var projectServiceToTest = new ProjectService(mockRepository, mockGroupRepository, mockConfig);
        
        // when
        var exception = catchThrowable(() -> projectServiceToTest.createGroup(LocalDateTime.now(), 0));
        
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }
    
    @Test
    @DisplayName("should create a new group from project.")
    void createGroup_configurationOk_existingProject_createsAndSavesGroup() {
        // given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        TaskGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
        
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        
        // when
        
        // then
        
    }
    
    private static TaskGroupRepository groupRepositoryReturning(final boolean value) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(value);
        return mockGroupRepository;
    }
    
    private static TaskConfigurationProperties configurationReturning(final boolean value) {
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(value); // pierwszy warunek z IFa
        
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        
        return mockConfig;
    }
    
    private TaskGroupRepository inMemoryGroupRepository() {
        return new TaskGroupRepository() {
            private int index = 0;
            private Map<Integer, TaskGroup> map = new HashMap<>();
            
            @Override
            public List<TaskGroup> findAll() {
                return new ArrayList<>(map.values());
            }
            
            @Override
            public Optional<TaskGroup> findById(final Integer id) {
                return Optional.ofNullable(map.get(id));
            }
            
            @Override
            public TaskGroup save(final TaskGroup entity) {
                if (entity.getId() == 0) {
                    try {
                        TaskGroup.class.getDeclaredField("id").set(entity, ++index); // refleksja
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                map.put(entity.getId(), entity);
                return entity;
            }
            
            @Override
            public boolean existsByDoneIsFalseAndProject_Id(final Integer projectId) {
                return map.values().stream()
                        .filter(taskGroup -> !taskGroup.isDone())
                        .anyMatch(taskGroup -> taskGroup.getProject() != null && taskGroup.getProject().getId() == projectId);
            }
        };
    }
}
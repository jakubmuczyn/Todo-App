package pl.jakubmuczyn.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.jakubmuczyn.TaskConfigurationProperties;
import pl.jakubmuczyn.model.*;
import pl.jakubmuczyn.model.projection.GroupReadModel;

import java.time.LocalDate;
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
    void createGroup_noMultipleGroupsConfig_and_undoneGroupExists_throwsIllegalStateException() {
        // given
        TaskGroupRepository mockGroupRepository = taskGroupRepositoryReturning(true);
        // and
        TaskConfigurationProperties mockConfig = taskConfigurationPropertiesReturning(false);
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
        // and
        TaskConfigurationProperties mockConfig = taskConfigurationPropertiesReturning(true);
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
    void createGroup_noMultipleGroupsConfig_and_noUndoneGroupExists_noProjects_throwsIllegalArgumentException() {
        // given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        // and
        TaskGroupRepository mockGroupRepository = taskGroupRepositoryReturning(false);
        // and
        TaskConfigurationProperties mockConfig = taskConfigurationPropertiesReturning(true);
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
        var today = LocalDate.now().atStartOfDay();
        // and
        var project = projectWith("project description", Set.of(-1, -2));
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(project));
        // and
        InMemoryGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
        int countBeforeCall = inMemoryGroupRepo.count();
        // and
        TaskConfigurationProperties mockConfig = taskConfigurationPropertiesReturning(true);
        // system under test
        var projectServiceToTest = new ProjectService(mockRepository, inMemoryGroupRepo, mockConfig);
        
        // when
        GroupReadModel result = projectServiceToTest.createGroup(today, 1);
        
        // then
        assertThat(result.getDescription()).isEqualTo("project description");
        assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));
        assertThat(result.getTasks()).allMatch(task -> task.getDescription().equals("test description"));
        assertThat(countBeforeCall + 1).isEqualTo(inMemoryGroupRepo.count());
    }
    
    private Project projectWith(String projectDescription, Set<Integer> daysToDeadline) {
        Set<ProjectStep> steps = daysToDeadline.stream()
                .map(days -> {
                    var step = mock(ProjectStep.class);
                    when(step.getDescription()).thenReturn("test description");
                    when(step.getDaysToDeadline()).thenReturn(days);
                    return step;
                }).collect(Collectors.toSet());
        var result = mock(Project.class);
        when(result.getDescription()).thenReturn(projectDescription);
        when(result.getSteps()).thenReturn(steps);
        return result;
    }
    
    private static TaskGroupRepository taskGroupRepositoryReturning(final boolean value) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(value);
        return mockGroupRepository;
    }
    
    private static TaskConfigurationProperties taskConfigurationPropertiesReturning(final boolean value) {
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(value); // pierwszy warunek z IFa
        
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        
        return mockConfig;
    }
    
    private InMemoryGroupRepository inMemoryGroupRepository() {
        return new InMemoryGroupRepository();
    }
    
    private static class InMemoryGroupRepository implements TaskGroupRepository {
        private int index = 0;
        private Map<Integer, TaskGroup> map = new HashMap<>();
        
        public int count() {
            return map.values().size();
        }
        
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
                    var field = TaskGroup.class.getDeclaredField("id"); // refleksja
                    field.setAccessible(true);
                    field.set(entity, ++index);
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
        
        ;
    }
}
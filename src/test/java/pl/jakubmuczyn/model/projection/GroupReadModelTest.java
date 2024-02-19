package pl.jakubmuczyn.model.projection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.jakubmuczyn.model.Group;
import pl.jakubmuczyn.model.Task;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GroupReadModelTest {

    @Test
    @DisplayName("should create null deadline for group when no task deadlines.")
    void constructor_noDeadlines_createsNullDeadline() {
        // given
        var source = new Group();
        source.setDescription("test description 1");
        source.setTasks(Set.of(new Task("test description 2", null)));
        
        // when
        var result = new GroupReadModel(source);
        
        // then
        assertThat(result).hasFieldOrPropertyWithValue("deadline", null);
        
        
    }
}
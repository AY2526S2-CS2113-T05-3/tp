package seedu.gitswole.assets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
//@@author vet3whale
@DisplayName("Exercise")
class ExerciseTest {

    @Test
    @DisplayName("Constructor sets all fields correctly")
    void constructor_setsAllFields() {
        Exercise e = new Exercise("bench press", 60, 4, 10);
        assertEquals("bench press", e.getExerciseName());
        assertEquals(60, e.getWeight());
        assertEquals(4, e.getSets());
        assertEquals(10, e.getReps());
    }

    @Test
    @DisplayName("Setters update each field")
    void setters_updateFields() {
        Exercise e = new Exercise("squat", 0, 0, 0);
        e.setExerciseName("deadlift");
        e.setWeight(100);
        e.setSets(5);
        e.setReps(5);

        assertEquals("deadlift", e.getExerciseName());
        assertEquals(100, e.getWeight());
        assertEquals(5, e.getSets());
        assertEquals(5, e.getReps());
    }

    @Test
    @DisplayName("Zero values are stored as-is")
    void constructor_allowsZeroValues() {
        Exercise e = new Exercise("plank", 0, 0, 0);
        assertEquals(0, e.getWeight());
        assertEquals(0, e.getSets());
        assertEquals(0, e.getReps());
    }
}
//@@author

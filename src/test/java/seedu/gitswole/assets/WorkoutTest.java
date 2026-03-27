package seedu.gitswole.assets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@author vet3whale
@DisplayName("Workout")
class WorkoutTest {

    private Workout workout;

    @BeforeEach
    void setUp() {
        workout = new Workout("push");
    }

    @Test
    @DisplayName("New workout has correct name and empty exercise list")
    void newWorkout_emptyList() {
        assertEquals("push", workout.getWorkoutName());
        assertTrue(workout.getExerciseList().isEmpty());
        assertEquals(0, workout.getNumOfExercises());
    }

    @Test
    @DisplayName("addExercise increases count and stores exercise")
    void addExercise_increasesCount() {
        workout.addExercise(new Exercise("bench press", 60, 3, 8));
        assertEquals(1, workout.getNumOfExercises());
        assertEquals("bench press", workout.getExerciseList().get(0).getExerciseName());
    }

    @Test
    @DisplayName("removeExercise removes matching exercise case-insensitively")
    void removeExercise_caseInsensitive() {
        workout.addExercise(new Exercise("Bench Press", 60, 3, 8));
        assertTrue(workout.removeExercise("bench press"));
        assertTrue(workout.getExerciseList().isEmpty());
    }

    @Test
    @DisplayName("removeExercise returns false when exercise not found")
    void removeExercise_notFound_returnsFalse() {
        workout.addExercise(new Exercise("squat", 80, 3, 5));
        assertFalse(workout.removeExercise("deadlift"));
        assertEquals(1, workout.getNumOfExercises());
    }

    @Test
    @DisplayName("setWorkoutName updates the name")
    void setWorkoutName_updatesName() {
        workout.setWorkoutName("pull");
        assertEquals("pull", workout.getWorkoutName());
    }

    @Test
    @DisplayName("Multiple exercises can be added")
    void addMultipleExercises() {
        workout.addExercise(new Exercise("bench press", 60, 3, 8));
        workout.addExercise(new Exercise("overhead press", 40, 3, 10));
        workout.addExercise(new Exercise("tricep dip", 0, 3, 12));
        assertEquals(3, workout.getNumOfExercises());
    }
}
//@@author

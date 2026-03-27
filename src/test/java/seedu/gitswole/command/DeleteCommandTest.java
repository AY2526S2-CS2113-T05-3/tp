package seedu.gitswole.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import seedu.gitswole.assets.WorkoutList;
import seedu.gitswole.ui.Ui;

public class DeleteCommandTest {
    private WorkoutList workouts;
    private Ui ui;

    @BeforeEach
    public void setUp() {
        workouts = new WorkoutList();
        ui = new Ui();

    }

    @Test
    public void execute_validWorkoutDelete_runsWithoutException() {
        DeleteCommand command = new DeleteCommand("delete w/Legs");

        assertDoesNotThrow(() -> command.execute(workouts, ui));
    }

    @Test
    public void execute_validExerciseDelete_runsWithoutException() {
        DeleteCommand command = new DeleteCommand("delete e/Squats w/Legs");

        assertDoesNotThrow(() -> command.execute(workouts, ui));
    }

    @Test
    public void execute_missingWorkoutName_handledGracefully() {
        DeleteCommand command = new DeleteCommand("delete w/");

        assertDoesNotThrow(() -> command.execute(workouts, ui));
    }

    @Test
    public void execute_invalidFormat_handledGracefully() {
        DeleteCommand command = new DeleteCommand("delete random string");

        assertDoesNotThrow(() -> command.execute(workouts, ui));
    }
}

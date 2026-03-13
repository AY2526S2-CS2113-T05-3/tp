//package seedu.GitSwole.command;
//
//// These are the crucial JUnit 5 imports you need to fix the red errors
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//// These import your specific project classes
//import seedu.GitSwole.assets.WorkoutList;
//import seedu.GitSwole.ui.Ui;
//import seedu.GitSwole.exceptions.GitSwoleException;
//
//public class DukeTest {
//    private WorkoutList workouts;
//    private Ui ui;
//
//    @BeforeEach
//    public void setUp() {
//        // This runs before EVERY single @Test method to give you a fresh slate
//        workouts = new WorkoutList();
//        ui = new Ui();
//
//        // NOTE: If you want to test that the size of the list actually decreases,
//        // you will need to add some dummy data here first using whatever method
//        // your team created for adding workouts.
//        // Example: workouts.addWorkout("Legs");
//    }
//
//    @Test
//    public void execute_validWorkoutDelete_runsWithoutException() {
//        DeleteCommand command = new DeleteCommand("delete w/Legs");
//
//        // Verifies that the command executes perfectly without crashing
//        assertDoesNotThrow(() -> command.execute(workouts, ui));
//    }
//
//    @Test
//    public void execute_validExerciseDelete_runsWithoutException() {
//        DeleteCommand command = new DeleteCommand("delete e/Squats w/Legs");
//
//        assertDoesNotThrow(() -> command.execute(workouts, ui));
//    }
//
//    @Test
//    public void execute_missingWorkoutName_handledGracefully() {
//        DeleteCommand command = new DeleteCommand("delete w/");
//
//        assertDoesNotThrow(() -> command.execute(workouts, ui));
//    }
//
//    @Test
//    public void execute_invalidFormat_handledGracefully() {
//        DeleteCommand command = new DeleteCommand("delete random string");
//
//        assertDoesNotThrow(() -> command.execute(workouts, ui));
//    }
//}
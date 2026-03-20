package seedu.gitswole.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import seedu.gitswole.assets.Exercise;
import seedu.gitswole.assets.Workout;
import seedu.gitswole.assets.WorkoutList;
import seedu.gitswole.exceptions.GitSwoleException;
import seedu.gitswole.ui.Ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("EditCommand")
class EditCommandTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;
    private WorkoutList workouts;
    private Ui ui;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ui = new Ui();
        workouts = new WorkoutList();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    /** Sets System.in BEFORE constructing EditCommand (Scanner binds in constructor). */
    private EditCommand editCommandWithInput(String response, String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ui = new Ui(in);
        return new EditCommand(response);
    }

    // edit workout tests

    @Test
    @DisplayName("edit w/WORKOUT - renames workout when new name is provided")
    void editWorkout_validName_renamesWorkout() throws GitSwoleException {
        workouts.addWorkout(new Workout("LegDay"));
        editCommandWithInput("edit w/LegDay", "ChestDay\n").execute(workouts, ui);
        assertNotNull(workouts.getWorkoutByName("ChestDay"));
    }

    @Test
    @DisplayName("edit w/WORKOUT - press enter cancels rename, workout name unchanged")
    void editWorkout_pressEnter_noChange() throws GitSwoleException {
        workouts.addWorkout(new Workout("LegDay"));
        editCommandWithInput("edit w/LegDay", "\n").execute(workouts, ui);
        assertNotNull(workouts.getWorkoutByName("LegDay"));
        assertTrue(outContent.toString().contains("No Changes recorded!"));
    }

    @Test
    @DisplayName("edit w/ - throws INCOMPLETE_COMMAND when workout name is blank")
    void editWorkout_blankName_throwsIncompleteCommand() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
            () -> editCommandWithInput("edit w/", "\n").execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, ex.getType());
    }

    @Test
    @DisplayName("edit with no w/ - throws INCOMPLETE_COMMAND")
    void editWorkout_missingPrefix_throwsIncompleteCommand() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
            () -> editCommandWithInput("edit LegDay", "\n").execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, ex.getType());
    }

    @Test
    @DisplayName("edit w/UNKNOWN - throws NOT_FOUND when workout does not exist")
    void editWorkout_unknownWorkout_throwsNotFound() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
            () -> editCommandWithInput("edit w/Ghost", "\n").execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.NOT_FOUND, ex.getType());
    }

    // edit exercise tests

    @Test
    @DisplayName("edit w/ e/EXERCISE - throws INCOMPLETE_COMMAND when workout name is blank")
    void editExercise_blankWorkoutName_throwsIncompleteCommand() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
            () -> editCommandWithInput("edit w/ e/squat", "\n").execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, ex.getType());
    }

    @Test
    @DisplayName("edit w/WORKOUT e/ - throws INCOMPLETE_COMMAND when exercise name is blank")
    void editExercise_blankExerciseName_throwsIncompleteCommand() {
        workouts.addWorkout(new Workout("push"));
        GitSwoleException ex = assertThrows(GitSwoleException.class,
            () -> editCommandWithInput("edit w/push e/", "\n").execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, ex.getType());
    }

    @Test
    @DisplayName("edit w/UNKNOWN e/EXERCISE - throws NOT_FOUND when workout does not exist")
    void editExercise_unknownWorkout_throwsNotFound() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
            () -> editCommandWithInput("edit w/Ghost e/squat", "\n").execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.NOT_FOUND, ex.getType());
    }

    @Test
    @DisplayName("edit w/WORKOUT e/EXERCISE - renames exercise when new name is provided")
    void editExercise_validName_renamesExercise() throws GitSwoleException {
        Workout push = new Workout("push");
        push.addExercise(new Exercise("bench press", 60, 3, 8));
        workouts.addWorkout(push);
        editCommandWithInput("edit w/push e/bench press", "\npress\n\n\n\n").execute(workouts, ui);
        assertNotNull(workouts.getWorkoutByName("push").getExerciseByName("press"));
    }

    @Test
    @DisplayName("edit w/WORKOUT e/EXERCISE - press enter on all fields leaves exercise unchanged")
    void editExercise_cancelAll_noChanges() throws GitSwoleException {
        Workout push = new Workout("push");
        push.addExercise(new Exercise("bench press", 60, 3, 8));
        workouts.addWorkout(push);
        editCommandWithInput("edit w/push e/bench press", "\n\n\n\n\n").execute(workouts, ui);
        Exercise e = workouts.getWorkoutByName("push").getExerciseByName("bench press");
        assertNotNull(e);
        assertEquals("bench press", e.getExerciseName());
    }

    @Test
    @DisplayName("edit w/WORKOUT e/EXERCISE - success message contains old and new exercise names")
    void editExercise_successMessage_containsNames() throws GitSwoleException {
        Workout push = new Workout("push");
        push.addExercise(new Exercise("bench press", 60, 3, 8));
        workouts.addWorkout(push);
        editCommandWithInput("edit w/push e/bench press", "\npress\n\n\n\n").execute(workouts, ui);
        String output = outContent.toString();
        assertTrue(output.contains("press"));
    }

    @Test
    @DisplayName("edit w/WORKOUT e/EXERCISE - correctly updates weight when new value is provided")
    void editExercise_newWeight_updatesWeight() throws GitSwoleException {
        Workout push = new Workout("push");
        push.addExercise(new Exercise("bench press", 60, 3, 8));
        workouts.addWorkout(push);
        editCommandWithInput("edit w/push e/bench press", "\n\n80\n\n\n").execute(workouts, ui);
        Exercise e = workouts.getWorkoutByName("push").getExerciseByName("bench press");
        assertEquals(80, e.getWeight());
    }

    @Test
    @DisplayName("edit w/WORKOUT e/EXERCISE - correctly updates sets when new value is provided")
    void editExercise_newSets_updatesSets() throws GitSwoleException {
        Workout push = new Workout("push");
        push.addExercise(new Exercise("bench press", 60, 3, 8));
        workouts.addWorkout(push);
        editCommandWithInput("edit w/push e/bench press", "\n\n\n5\n\n").execute(workouts, ui);
        Exercise e = workouts.getWorkoutByName("push").getExerciseByName("bench press");
        assertEquals(5, e.getSets());
    }

    @Test
    @DisplayName("edit w/WORKOUT e/EXERCISE - correctly updates reps when new value is provided")
    void editExercise_newReps_updatesReps() throws GitSwoleException {
        Workout push = new Workout("push");
        push.addExercise(new Exercise("bench press", 60, 3, 8));
        workouts.addWorkout(push);
        editCommandWithInput("edit w/push e/bench press", "\n\n\n\n12\n").execute(workouts, ui);
        Exercise e = workouts.getWorkoutByName("push").getExerciseByName("bench press");
        assertEquals(12, e.getReps());
    }

    @Test
    @DisplayName("edit w/WORKOUT e/EXERCISE - correctly updates all fields at once")
    void editExercise_allFields_updatesAll() throws GitSwoleException {
        Workout push = new Workout("push");
        push.addExercise(new Exercise("bench press", 60, 3, 8));
        workouts.addWorkout(push);
        editCommandWithInput("edit w/push e/bench press", "chest\nincline press\n80\n5\n12\n").execute(workouts, ui);
        Exercise e = workouts.getWorkoutByName("chest").getExerciseByName("incline press");
        assertNotNull(e);
        assertEquals(80, e.getWeight());
        assertEquals(5, e.getSets());
        assertEquals(12, e.getReps());
    }

}

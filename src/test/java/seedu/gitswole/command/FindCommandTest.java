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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FindCommand")
class FindCommandTest {

    private final PrintStream originalOut = System.out;
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
    }

    /**
     * Populates the list with two workouts and a few exercises.
     */
    private void populateWorkouts() {
        Workout push = new Workout("push day");
        push.addExercise(new Exercise("bench press", 60, 3, 8));
        push.addExercise(new Exercise("overhead press", 40, 3, 10));
        workouts.addWorkout(push);

        Workout pull = new Workout("pull day");
        pull.addExercise(new Exercise("deadlift", 100, 3, 5));
        pull.addExercise(new Exercise("barbell row", 70, 3, 8));
        workouts.addWorkout(pull);
    }

    // find workout  (find w/KEYWORD)
    @Test
    @DisplayName("find w/KEYWORD — shows matching workout name and exercise count")
    void findWorkout_matchFound_showsResult() throws GitSwoleException {
        populateWorkouts();
        new FindCommand("find w/push").execute(workouts, ui);
        String output = outContent.toString();
        assertTrue(output.contains("push day"));
        assertTrue(output.contains("2"));
    }

    @Test
    @DisplayName("find w/KEYWORD — partial keyword matches workout")
    void findWorkout_partialKeyword_matches() throws GitSwoleException {
        populateWorkouts();
        new FindCommand("find w/day").execute(workouts, ui);
        String output = outContent.toString();
        assertTrue(output.contains("push day"));
        assertTrue(output.contains("pull day"));
    }

    @Test
    @DisplayName("find w/KEYWORD — search is case-insensitive")
    void findWorkout_caseInsensitive() throws GitSwoleException {
        populateWorkouts();
        new FindCommand("find w/PUSH").execute(workouts, ui);
        assertTrue(outContent.toString().contains("push day"));
    }

    @Test
    @DisplayName("find w/KEYWORD — shows 'Workout Not Found' when no match")
    void findWorkout_noMatch_showsNotFound() throws GitSwoleException {
        populateWorkouts();
        new FindCommand("find w/legs").execute(workouts, ui);
        assertTrue(outContent.toString().contains("Workout Not Found"));
    }

    @Test
    @DisplayName("find w/ — throws INCOMPLETE_COMMAND when keyword is blank")
    void findWorkout_blankKeyword_throwsIncompleteCommand() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
            () -> new FindCommand("find w/").execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, ex.getType());
    }

    @Test
    @DisplayName("find with missing w/ prefix — throws INCOMPLETE_COMMAND")
    void findWorkout_missingPrefix_throwsIncompleteCommand() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
            () -> new FindCommand("find push").execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, ex.getType());
    }

    @Test
    @DisplayName("find w/KEYWORD — empty workout list shows 'Workout Not Found'")
    void findWorkout_emptyList_showsNotFound() throws GitSwoleException {
        new FindCommand("find w/push").execute(workouts, ui);
        assertTrue(outContent.toString().contains("Workout Not Found"));
    }

    // find exercise  (find e/KEYWORD w/WORKOUT)
    @Test
    @DisplayName("find e/KEYWORD w/WORKOUT — shows matching exercise details")
    void findExercise_matchFound_showsDetails() throws GitSwoleException {
        populateWorkouts();
        new FindCommand("find e/bench w/push day").execute(workouts, ui);
        String output = outContent.toString();
        assertTrue(output.contains("bench press"));
        assertTrue(output.contains("60")); // weight
        assertTrue(output.contains("3")); // sets
        assertTrue(output.contains("8")); // reps
    }

    @Test
    @DisplayName("find e/KEYWORD w/WORKOUT — partial keyword matches exercise")
    void findExercise_partialKeyword_matches() throws GitSwoleException {
        populateWorkouts();
        new FindCommand("find e/press w/push day").execute(workouts, ui);
        String output = outContent.toString();
        assertTrue(output.contains("bench press"));
        assertTrue(output.contains("overhead press"));
    }

    @Test
    @DisplayName("find e/KEYWORD w/WORKOUT — search is case-insensitive")
    void findExercise_caseInsensitive() throws GitSwoleException {
        populateWorkouts();
        new FindCommand("find e/BENCH w/push day").execute(workouts, ui);
        assertTrue(outContent.toString().contains("bench press"));
    }

    @Test
    @DisplayName("find e/KEYWORD w/WORKOUT — shows 'Exercise Not Found' when no match")
    void findExercise_noMatch_showsNotFound() throws GitSwoleException {
        populateWorkouts();
        new FindCommand("find e/squat w/push day").execute(workouts, ui);
        assertTrue(outContent.toString().contains("Exercise Not Found"));
    }

    @Test
    @DisplayName("find e/KEYWORD w/UNKNOWN — throws IDX_OUTOFBOUNDS for missing workout")
    void findExercise_unknownWorkout_throwsIdxOutOfBounds() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
            () -> new FindCommand("find e/bench w/legs").execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.IDX_OUTOFBOUNDS, ex.getType());
    }

    @Test
    @DisplayName("find e/ w/WORKOUT — throws INCOMPLETE_COMMAND when exercise keyword is blank")
    void findExercise_blankExerciseKeyword_throwsIncompleteCommand() {
        workouts.addWorkout(new Workout("push day"));
        GitSwoleException ex = assertThrows(GitSwoleException.class,
            () -> new FindCommand("find e/ w/push day").execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, ex.getType());
    }

    @Test
    @DisplayName("find e/KEYWORD — throws INCOMPLETE_COMMAND when w/ prefix is absent")
    void findExercise_missingWorkoutPrefix_throwsIncompleteCommand() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
            () -> new FindCommand("find e/bench").execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, ex.getType());
    }

    @Test
    @DisplayName("find e/KEYWORD w/WORKOUT — does not show exercises from other workouts")
    void findExercise_isolatesWorkout() throws GitSwoleException {
        populateWorkouts();
        new FindCommand("find e/press w/push day").execute(workouts, ui);
        assertFalse(outContent.toString().contains("deadlift"));
        assertFalse(outContent.toString().contains("barbell row"));
    }

    // isExit
    @Test
    @DisplayName("isExit always returns false")
    void findCommand_isExitFalse() throws GitSwoleException {
        populateWorkouts();
        FindCommand cmd = new FindCommand("find w/push");
        cmd.execute(workouts, ui);
        assertFalse(cmd.isExit());
    }
}

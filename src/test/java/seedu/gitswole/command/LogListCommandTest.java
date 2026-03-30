package seedu.gitswole.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seedu.gitswole.assets.WorkoutList;
import seedu.gitswole.exceptions.GitSwoleException;
import seedu.gitswole.storage.HistoryStorage;
import seedu.gitswole.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("LogListCommand")
class LogListCommandTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private WorkoutList workouts;
    private Ui ui;
    private HistoryStorageStub historyStub;

    private static class HistoryStorageStub extends HistoryStorage {
        private List<String> allEntries = new ArrayList<>();
        private List<String> workoutEntries = new ArrayList<>();
        private List<String> dateEntries = new ArrayList<>();
        private boolean throwOnAllEntries;
        private boolean throwOnWorkoutEntries;
        private boolean throwOnDateEntries;

        @Override
        public List<String> getAllEntries() throws IOException {
            if (throwOnAllEntries) {
                throw new IOException("stub all entries error");
            }
            return allEntries;
        }

        @Override
        public List<String> getEntriesByWorkout(String workoutName) throws IOException {
            if (throwOnWorkoutEntries) {
                throw new IOException("stub workout entries error");
            }
            return workoutEntries;
        }

        @Override
        public List<String> getEntriesByDate(String date) throws IOException {
            if (throwOnDateEntries) {
                throw new IOException("stub date entries error");
            }
            return dateEntries;
        }
    }

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        workouts = new WorkoutList();
        ui = new Ui();
        historyStub = new HistoryStorageStub();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("loglist - empty history shows no sessions message")
    void execute_emptyHistory_showsNoSessionsMessage() throws GitSwoleException {
        new LogListCommand("loglist", historyStub).execute(workouts, ui);
        assertTrue(outContent.toString().contains("No workout sessions have been logged yet."));
    }

    @Test
    @DisplayName("loglist - non-empty history shows complete header and entries")
    void execute_nonEmptyHistory_showsHeaderAndEntries() throws GitSwoleException {
        historyStub.allEntries = List.of("[24-03-2026, 09:00] PUSH workout", "bench: 60kg");

        new LogListCommand("loglist", historyStub).execute(workouts, ui);

        String output = outContent.toString();
        assertTrue(output.contains("=== COMPLETE LOG HISTORY ==="));
        assertTrue(output.contains("[24-03-2026, 09:00] PUSH workout"));
        assertTrue(output.contains("bench: 60kg"));
    }

    @Test
    @DisplayName("loglist w/push - non-empty entries shows workout header and entries")
    void execute_workoutFilterWithEntries_showsWorkoutHeaderAndEntries() throws GitSwoleException {
        historyStub.workoutEntries = List.of("[24-03-2026, 09:00] PUSH workout", "bench: 60kg");

        new LogListCommand("loglist w/push", historyStub).execute(workouts, ui);

        String output = outContent.toString();
        assertTrue(output.contains("=== LOG HISTORY FOR: PUSH ==="));
        assertTrue(output.contains("bench: 60kg"));
    }

    @Test
    @DisplayName("loglist w/push - empty entries shows no sessions for workout")
    void execute_workoutFilterEmpty_showsWorkoutEmptyMessage() throws GitSwoleException {
        historyStub.workoutEntries = List.of();

        new LogListCommand("loglist w/push", historyStub).execute(workouts, ui);

        assertTrue(outContent.toString().contains("No logged sessions found for workout: push"));
    }

    @Test
    @DisplayName("loglist w/ - missing workout name throws INCOMPLETE_COMMAND")
    void execute_missingWorkoutName_throwsIncompleteCommand() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
                () -> new LogListCommand("loglist w/", historyStub).execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, ex.getType());
    }

    @Test
    @DisplayName("loglist d/24-03-2026 - non-empty entries shows date header and entries")
    void execute_dateFilterWithEntries_showsDateHeaderAndEntries() throws GitSwoleException {
        historyStub.dateEntries = List.of("[24-03-2026, 09:00] PUSH workout", "bench: 60kg");

        new LogListCommand("loglist d/24-03-2026", historyStub).execute(workouts, ui);

        String output = outContent.toString();
        assertTrue(output.contains("=== LOG HISTORY FOR: 24-03-2026 ==="));
        assertTrue(output.contains("bench: 60kg"));
    }

    @Test
    @DisplayName("loglist d/24-03-2026 - empty entries shows no sessions for date")
    void execute_dateFilterEmpty_showsDateEmptyMessage() throws GitSwoleException {
        historyStub.dateEntries = List.of();

        new LogListCommand("loglist d/24-03-2026", historyStub).execute(workouts, ui);

        assertTrue(outContent.toString().contains("No logged sessions found for this date: 24-03-2026"));
    }

    @Test
    @DisplayName("loglist d/ - missing date throws INCOMPLETE_COMMAND")
    void execute_missingDate_throwsIncompleteCommand() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
                () -> new LogListCommand("loglist d/", historyStub).execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, ex.getType());
    }

    @Test
    @DisplayName("loglist foo - unrecognised arguments throws INCOMPLETE_COMMAND")
    void execute_unrecognisedArguments_throwsIncompleteCommand() {
        GitSwoleException ex = assertThrows(GitSwoleException.class,
                () -> new LogListCommand("loglist foo", historyStub).execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, ex.getType());
        assertEquals("Command has unrecognised arguments", ex.getCommand());
    }

    @Test
    @DisplayName("loglist with trailing spaces behaves as all entries")
    void execute_allWithTrailingSpaces_behavesAsAll() throws GitSwoleException {
        historyStub.allEntries = List.of();

        new LogListCommand("loglist   ", historyStub).execute(workouts, ui);

        assertTrue(outContent.toString().contains("No workout sessions have been logged yet."));
    }

    @Test
    @DisplayName("loglist - io exception while reading history throws DEFAULT")
    void execute_storageIOException_throwsDefaultException() {
        historyStub.throwOnAllEntries = true;

        GitSwoleException ex = assertThrows(GitSwoleException.class,
                () -> new LogListCommand("loglist", historyStub).execute(workouts, ui));
        assertEquals(GitSwoleException.ErrorType.DEFAULT, ex.getType());
        assertTrue(ex.getCommand().startsWith("Could not read history log:"));
    }
}

package seedu.gitswole.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import seedu.gitswole.assets.WorkoutList;
import seedu.gitswole.exceptions.GitSwoleException;
import seedu.gitswole.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@author vet3whale
@DisplayName("ExitCommand")
class ExitCommandTest {

    private final PrintStream originalOut = System.out;

    @BeforeEach
    void suppressOutput() {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("isExit returns false before execute is called")
    void isExit_falseBeforeExecute() {
        ExitCommand cmd = new ExitCommand();
        assertFalse(cmd.isExit());
    }

    @Test
    @DisplayName("execute sets isExit to true")
    void execute_setsIsExitTrue() throws GitSwoleException {
        ExitCommand cmd = new ExitCommand();
        cmd.execute(new WorkoutList(), new Ui());
        assertTrue(cmd.isExit());
    }

    @Test
    @DisplayName("execute does not throw any exception")
    void execute_doesNotThrow() {
        ExitCommand cmd = new ExitCommand();
        assertDoesNotThrow(() -> cmd.execute(new WorkoutList(), new Ui()));
    }

    @Test
    @DisplayName("isExit remains true after multiple execute calls")
    void isExit_remainsTrueAfterMultipleExecutions() throws GitSwoleException {
        ExitCommand cmd = new ExitCommand();
        cmd.execute(new WorkoutList(), new Ui());
        cmd.execute(new WorkoutList(), new Ui());
        assertTrue(cmd.isExit());
    }
}
//@@author

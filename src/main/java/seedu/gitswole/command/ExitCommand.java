package seedu.gitswole.command;

import seedu.gitswole.assets.WorkoutList;
import seedu.gitswole.exceptions.GitSwoleException;
import seedu.gitswole.ui.Ui;

//@@author vet3whale
/**
 * Represents a command that terminates the GitSwole application.
 * Sets the exit flag to {@code true}, signalling the main loop to stop.
 */
public class ExitCommand extends Command {
    /**
     * Constructs an ExitCommand.
     */
    public ExitCommand() {
    }

    /**
     * Executes the exit command by setting the exit flag and displaying a goodbye message.
     *
     * @param workouts The current list of workouts (unused).
     * @param ui       The user interface used to display the farewell message.
     * @throws GitSwoleException Not thrown by this command.
     */
    @Override
    public void execute(WorkoutList workouts, Ui ui) throws GitSwoleException {
        super.isExit = true;
    }

}
//@@author

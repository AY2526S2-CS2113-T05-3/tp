package seedu.gitswole.command;

import seedu.gitswole.assets.Workout;
import seedu.gitswole.assets.WorkoutList;
import seedu.gitswole.exceptions.GitSwoleException;
import seedu.gitswole.parser.Parser;
import seedu.gitswole.ui.Ui;

import java.util.ArrayList;

/**
 * Represents a command that lists workouts or exercises based on user input.
 * Supported formats:
 * list — lists names of all workout sessions
 * list w/WORKOUT — lists all exercises within a specific workout
 * list all — lists all exercises across all workout sessions
 */
public class ListCommand extends Command {
    private String arguments;

    /**
     * Constructs a ListCommand with the raw user input string.
     *
     * @param arguments The full command string entered by the user.
     */
    public ListCommand(String arguments) {
        this.arguments = arguments.trim().toLowerCase();
    }

    /**
     * Executes the list command by determining the scope (summary, specific workout. or all)
     *
     * @param workouts The current list of workouts
     * @param ui       The user interface for displaying results
     * @throws GitSwoleException If a specified workout cannot be found
     */
    @Override
    public void execute(WorkoutList workouts, Ui ui) throws GitSwoleException {
        if (arguments.contains("w/")) {
            handleListWorkout(workouts, ui);
        } else if (arguments.endsWith("all")) {
            handleListAll(workouts, ui);
        } else {
            handleListSummary(workouts, ui);
        }
    }

    /**
     * Lists only the names of the workout sessions currently stored
     *
     * @param workouts The WorkoutList to retrieve the names from
     * @param ui       The instance to display the list
     */
    private void handleListSummary(WorkoutList workouts, Ui ui) {
        ArrayList<Workout> workoutList = workouts.getWorkouts();
        if (workoutList.isEmpty()) {
            ui.showMessage("Your workout list is currently empty :(");
            ui.showLine();
            return;
        }

        ui.showMessage("Your Workouts:");
        for (int i = 0; i < workoutList.size(); i++) {
            ui.showMessage((i + 1) + ". " + workoutList.get(i).getWorkoutName());
        }
        ui.showLine();
    }

    /**
     * List all exercises within a specific workout session identified by the "w/" prefix
     *
     * @param workouts The WorkoutList to search
     * @param ui       The instance to display results
     * @throws GitSwoleException If the workout name is missing or not found
     */
    private void handleListWorkout(WorkoutList workouts, Ui ui) throws GitSwoleException {
        String workoutName = Parser.parseValue(arguments, "w/");
        if (workoutName == null || workoutName.isEmpty()) {
            throw new GitSwoleException(GitSwoleException.ErrorType.INCOMPLETE_COMMAND, "list w/workout");
        }
        Workout workout = workouts.getWorkoutByName(workoutName);
        if (workout == null) {
            throw new GitSwoleException(GitSwoleException.ErrorType.IDX_OUTOFBOUNDS, workoutName);
        }
        ui.showMessage(workout.getWorkoutName().toUpperCase() + " Workout Exercises:");
        ui.printExercises(workout.getExerciseList());
        ui.showLine();
    }

    /**
     * Lists every exercise across every workout session stored in the application
     *
     * @param workouts The WorkoutList containing all sessions
     * @param ui       The Ui instance to display results
     */
    private void handleListAll(WorkoutList workouts, Ui ui) {
        ArrayList<Workout> workoutList = workouts.getWorkouts();
        if (workoutList.isEmpty()) {
            ui.showMessage("Your workout list is currently empty :(");
            return;
        }

        ui.printWorkouts(workoutList);
    }


}

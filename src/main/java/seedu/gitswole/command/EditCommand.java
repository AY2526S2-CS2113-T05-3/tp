package seedu.gitswole.command;

import seedu.gitswole.assets.Exercise;
import seedu.gitswole.assets.Workout;
import seedu.gitswole.assets.WorkoutList;
import seedu.gitswole.exceptions.GitSwoleException;
import seedu.gitswole.parser.Parser;
import seedu.gitswole.ui.Ui;

import java.util.logging.Level;

public class EditCommand extends Command{
    private String response;
    private boolean hasChanged = false;

    public EditCommand(String response){
        this.response = response;
    }

    @Override
    public void execute(WorkoutList workouts, Ui ui) throws GitSwoleException {
        if (response.contains(" e/")) {
            handleEditExercise(workouts, ui);
        } else {
            handleEditWorkout(workouts, ui);
        }
        ui.showLine();
    }

    private void handleEditWorkout(WorkoutList workouts, Ui ui) throws GitSwoleException {
        String workoutToEditString = Parser.parseValue(response, "w/");
        if (workoutToEditString == null || workoutToEditString.isEmpty()) {
            LOGGER.log(Level.WARNING, "EditWorkout failed: Missing 'w/' or 'e/' flag or value.");
            throw new GitSwoleException(
                GitSwoleException.ErrorType.INCOMPLETE_COMMAND,
                "Missing name of workout. Usage: edit w/WORKOUT_NAME or edit w/WORKOUT_NAME e/EXERCISE"
            );
        }

        Workout workoutToEdit = workouts.getWorkoutByName(workoutToEditString);
        if (workoutToEdit == null) {
            LOGGER.log(Level.WARNING, "EditWorkout failed: Workout provided does not exist.");
            throw new GitSwoleException(GitSwoleException.ErrorType.NOT_FOUND , workoutToEditString);
        }
        printCurrentWorkout(ui, workoutToEditString);

        changeWorkoutName(ui, workoutToEdit);
        printUpdatedWorkout(ui, workoutToEdit);
    }

    private void handleEditExercise(WorkoutList workouts, Ui ui) throws GitSwoleException {
        String workoutToEditString = Parser.parseValue(response, "w/");
        String exerciseToEditString = Parser.parseValue(response, "e/");

        boolean validInput = (workoutToEditString == null || workoutToEditString.isEmpty()) ||
            (exerciseToEditString == null || exerciseToEditString.isEmpty());
        if (validInput) {
            LOGGER.log(Level.WARNING, "EditWorkout failed: Missing 'w/' or 'e/' flag.");
            throw new GitSwoleException(
                GitSwoleException.ErrorType.INCOMPLETE_COMMAND,
                "Missing name of workout. Usage: edit w/WORKOUT_NAME or edit w/WORKOUT_NAME e/EXERCISE"
            );
        }

        Workout workoutToEdit = workouts.getWorkoutByName(workoutToEditString);
        if (workoutToEdit == null) {
            LOGGER.log(Level.WARNING, "EditWorkout failed: Workout provided does not exist.");
            throw new GitSwoleException(GitSwoleException.ErrorType.NOT_FOUND , workoutToEditString);
        }
        ui.showMessage(exerciseToEditString);
        Exercise exerciseToEdit = workoutToEdit.getExerciseByName(exerciseToEditString);
        if (exerciseToEdit == null) {
            LOGGER.log(Level.WARNING, "EditWorkout failed: Exercise provided does not exist.");
            throw new GitSwoleException(GitSwoleException.ErrorType.NOT_FOUND , exerciseToEditString);
        }

        printCurrentExercise(ui, workoutToEditString, exerciseToEdit);

        changeWorkoutName(ui, workoutToEdit);
        changeExerciseName(ui, exerciseToEdit);
        changeWeight(ui, exerciseToEdit);
        changeSets(ui, exerciseToEdit);
        changeReps(ui,exerciseToEdit);

        printUpdatedWorkout(ui, workoutToEdit);
    }

    private void changeReps(Ui ui, Exercise exerciseToEdit) {
        ui.showLine();
        ui.showMessage("Change REPS to: ");
        int newExerciseReps;
        try {
            newExerciseReps = Integer.parseInt(ui.readLine());
        } catch (NumberFormatException e) {
            ui.showMessage("No Change recorded!");
            return;
        }
        if (newExerciseReps <= 0) {
            return;
        }
        exerciseToEdit.setReps(newExerciseReps);
        this.hasChanged = true;
    }

    private void changeSets(Ui ui, Exercise exerciseToEdit) {
        ui.showLine();
        ui.showMessage("Change SETS to: ");
        int newExerciseSets;
        try {
            newExerciseSets = Integer.parseInt(ui.readLine());
        } catch (NumberFormatException e) {
            ui.showMessage("No Change recorded!");
            return;
        }
        if (newExerciseSets <= 0) {
            return;
        }
        exerciseToEdit.setSets(newExerciseSets);
        this.hasChanged = true;
    }

    private void changeWeight(Ui ui, Exercise exerciseToEdit) {
        ui.showLine();
        ui.showMessage("Change WEIGHT to: ");
        int newExerciseWeight;
        try {
            newExerciseWeight = Integer.parseInt(ui.readLine());
        } catch (NumberFormatException e) {
            ui.showMessage("No Change recorded!");
            return;
        }
        if (newExerciseWeight <= 0) {
            return;
        }
        exerciseToEdit.setWeight(newExerciseWeight);
        this.hasChanged = true;
    }

    private void changeExerciseName(Ui ui, Exercise exerciseToEdit) {
        ui.showLine();
        ui.showMessage("Change EXERCISE NAME to: ");
        String newExerciseName = ui.readLine();
        if (newExerciseName == null || newExerciseName.isEmpty()) {
            return;
        }
        exerciseToEdit.setExerciseName(newExerciseName);
        this.hasChanged = true;
    }

    private void changeWorkoutName(Ui ui, Workout workoutToEdit) {
        ui.showLine();
        ui.showMessage("Change WORKOUT NAME to: ");
        String newWorkoutName = ui.readLine();
        if (newWorkoutName == null || newWorkoutName.isEmpty()) {
            return;
        }

        workoutToEdit.setWorkoutName(newWorkoutName);
        this.hasChanged = true;
    }

    private static void printCurrentExercise(Ui ui, String workoutToEditString, Exercise exerciseToEdit) {
        ui.showMessage("CURRENT WORKOUT: " + workoutToEditString);
        ui.printExercise(exerciseToEdit);
        ui.showLine();
        ui.showMessage("Enter the new values below (press enter to NOT edit): ");
    }

    private static void printCurrentWorkout(Ui ui, String workoutToEditString) {
        ui.showMessage("CURRENT WORKOUT: " + workoutToEditString);
        ui.showMessage("Enter the new values below (press enter to NOT edit): ");
    }

    private void printUpdatedWorkout(Ui ui, Workout workoutToEdit) {
        if (hasChanged) {
            ui.showMessage("Change Recorded! Edited Workout:");
            ui.showLine();
            ui.printWorkout(workoutToEdit);
        } else {
            ui.showMessage("No Changes recorded!");
            ui.showLine();
        }
    }
}

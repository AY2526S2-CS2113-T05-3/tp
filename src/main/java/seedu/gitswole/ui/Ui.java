package seedu.gitswole.ui;

import seedu.gitswole.assets.Exercise;
import seedu.gitswole.assets.Workout;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user interface interactions for the GitSwole application,
 * including reading user input and displaying messages, workouts, and exercises.
 */
public class Ui {
    private Scanner in;
    private int dashes = 100; // minimally 100, for help print statements

    /**
     * Constructs an Ui instance and initializes the input scanner.
     */
    public Ui() {
        in = new Scanner(System.in);
    }

    /**
     * Reads and returns a single line of input from the user.
     *
     * @return The full command string entered by the user.
     */
    public String readCommand() {
        String response = in.nextLine();
        return response;
    }

    /**
     * Displays the application logo and a welcome message on startup.
     */
    public void helloGreeting() {
        showMessage(" _____ _ _   _____               _      ");
        showMessage("|  __ (_) | /  ___|             | |     ");
        showMessage("| |  \\/_| |_\\ `--.__      _____ | | ___ ");
        showMessage("| | __| | __|`--. \\ \\ /\\ / / _ \\| |/ _ \\");
        showMessage("| |_\\ \\ | |_/\\__/ /\\ V  V / (_) | |  __/");
        showMessage(" \\____/_|\\__\\____/  \\_/\\_/ \\___/|_|\\___|");
        showMessage("                                        ");
        showMessage("                                        ");

        showMessage("Welcome to GitSwole! LET'S GET THEM GAINS");
        showLine();
    }

    /**
     * Displays a horizontal separator line for visual clarity.
     */
    public void showLine() {
        showMessage("_".repeat(getDashes()));
    }

    /**
     * Displays a goodbye message when the application terminates.
     */
    public void byeGreeting() {
        showLine();
        showMessage("Bye! Keep getting swole!");
        showLine();
    }

    /**
     * Displays a formatted error message surrounded by separator lines.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        showLine();
        showMessage(" " + message);
        showLine();
    }

    /**
     * Displays a general informational message.
     *
     * @param s The message to display.
     */
    public void showMessage(String s) {
        System.out.println(" " + s);
    }

    /**
     * Prints all workouts in the provided list, each separated by a line.
     *
     * @param workouts The list of {@link Workout} objects to display.
     */
    public void printWorkouts(ArrayList<Workout> workouts) {
        showLine();
        for (Workout workout : workouts) {
            printWorkout(workout);
        }
        showLine();
    }

    /**
     * Prints a single workout's name and all its exercises.
     *
     * @param workout The {@link Workout} to display.
     */
    public void printWorkout(Workout workout) {
        String name = workout.getWorkoutName().toUpperCase();
        int padding = (dashes - name.length()) / 2;
        showMessage(" ".repeat(Math.max(0, padding)) + name);
        showLine();
        for (Exercise exercise : workout.getExerciseList()) {
            printExercise(exercise);
        }
    }

    /**
     * Prints a single exercise's details in a formatted row.
     *
     * @param exercise The {@link Exercise} to display.
     */
    public void printExercise(Exercise exercise) {
        String line = String.format("%-20s | %-6s | %-4s | %-4s",
            exercise.getExerciseName(),
            exercise.getWeight(),
            exercise.getSets(),
            exercise.getReps());
        showMessage(line);
    }

    /**
     * Returns the number of dashes used for separator lines and table widths.
     *
     * @return The dash count.
     */
    public int getDashes() {
        return dashes;
    }

}

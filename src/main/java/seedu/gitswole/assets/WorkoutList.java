package seedu.gitswole.assets;

import java.util.ArrayList;

/**
 * Manages the collection of {@link Workout} objects for the GitSwole application.
 * Provides methods to add, retrieve, and query workouts.
 */
public class WorkoutList {
    private ArrayList<Workout> workouts;
    private String activeWorkoutName = null;

    /**
     * Constructs an empty WorkoutList.
     */
    public WorkoutList() {
        workouts = new ArrayList<>();
    }

    /**
     * Sets the name of the workout currently being logged.
     *
     * @param name The name of the active workout.
     */
    public void setActiveWorkoutName(String name) {
        this.activeWorkoutName = name;
    }

    /**
     * Returns the name of the workout currently being logged.
     *
     * @return The active workout name, or {@code null} if no session is active.
     */
    public String getActiveWorkoutName() {
        return activeWorkoutName;
    }

    /**
     * Adds a workout to the list.
     *
     * @param workout The {@link Workout} to add.
     */
    public void addWorkout(Workout workout) {
        // assertions
        assert workout != null : "Workout to add must not be null";
        int sizeBefore = workouts.size();
        workouts.add(workout);
        assert workouts.size() == sizeBefore + 1 : "Workout list size should increase by 1 after add";
    }

    /**
     * Returns the full list of workouts.
     *
     * @return An {@link ArrayList} of all {@link Workout} objects.
     */
    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    /**
     * Returns the number of workouts currently in the list.
     *
     * @return The total count of workouts.
     */
    public int numOfWorkouts() {
        return workouts.size();
    }

    /**
     * Removes a workout from the list by its name.
     *
     * @param name The name of the workout to remove.
     * @return true if the workout was found and removed, false otherwise.
     */
    public boolean removeWorkout(String name) {
        // assertions
        assert name != null && !name.isBlank() : "Workout name must not be null or blank";
        int sizeBefore = workouts.size();
        Workout workoutToRemove = getWorkoutByName(name);
        if (workoutToRemove != null) {
            workouts.remove(workoutToRemove);
            assert workouts.size() == sizeBefore - 1 : "Workout list size should decrease by 1 after remove";
            return true;
        }
        return false;
    }

    /**
     * Removes a specific exercise from a specific workout.
     * @param workoutName The name of the workout containing the exercise.
     *
     * @param exerciseName The name of the exercise to remove.
     * @return true if successful, false if the workout or exercise was not found.
     */
    public boolean removeExercise(String workoutName, String exerciseName) {
        Workout workout = getWorkoutByName(workoutName);
        if (workout != null) {
            // This assumes your Workout class has a removeExercise method!
            return workout.removeExercise(exerciseName);
        }
        return false;
    }

    public Workout getWorkoutByName(String name) {
        for (Workout w : workouts) {
            if (w.getWorkoutName().equalsIgnoreCase(name.trim())) {
                return w;
            }
        }
        return null;
    }
}

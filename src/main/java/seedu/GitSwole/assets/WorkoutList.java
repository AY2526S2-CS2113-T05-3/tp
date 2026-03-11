package seedu.GitSwole.assets;

import java.util.ArrayList;

/**
 * Manages the collection of {@link Workout} objects for the GitSwole application.
 * Provides methods to add, retrieve, and query workouts.
 */
public class WorkoutList {
	private ArrayList<Workout> workouts;

	/**
	 * Constructs an empty WorkoutList.
	 */
	public WorkoutList() {
		workouts = new ArrayList<>();
	}

	/**
	 * Adds a workout to the list.
	 *
	 * @param workout The {@link Workout} to add.
	 */
	public void addWorkout(Workout workout){
		workouts.add(workout);
	}

	// TODO: delete workouts and exercises

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
	 * Searches for a workout by name, case-insensitively.
	 *
	 * @param name The name of the workout to find.
	 * @return The matching {@link Workout}, or {@code null} if not found.
	 */
//	public Workout getWorkoutByName(String name) {
//		Workout workoutToSearch = new Workout(name);
//		int idx = workouts.indexOf(workoutToSearch);
//		return workouts.get(idx);
//	}

	/**
	 * Removes a workout from the list by its name.
	 *
	 * @param name The name of the workout to remove.
	 * @return true if the workout was found and removed, false otherwise.
	 */
	public boolean removeWorkout(String name) {
		Workout workoutToRemove = getWorkoutByName(name);
		if (workoutToRemove != null) {
			workouts.remove(workoutToRemove);
			return true;
		}
		return false;
	}

	/**
	 * Removes a specific exercise from a specific workout.
	 * * @param workoutName The name of the workout containing the exercise.
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

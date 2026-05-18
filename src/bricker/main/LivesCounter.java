package bricker.main;

/**
 * A shared counter representing the number of lives remaining in the game.
 * This class provides simple operations for retrieving, increasing,
 * and decreasing the current number of lives.
 */
public class LivesCounter {
	private int value;

	/**
	 * Creates a new lives counter with the given initial value.
	 *
	 * @param initialValue The initial number of lives.
	 */
	public LivesCounter(int initialValue){
		this.value = initialValue;
	}


	/**
	 * Returns the current number of remaining lives.
	 *
	 * @return The current lives count.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Increases the number of lives by one.
	 */
	public void increment(){
		value++;
	}

	/**
	 * Decreases the number of lives by one.
	 */
	public void decrement(){
		value--;
	}
}

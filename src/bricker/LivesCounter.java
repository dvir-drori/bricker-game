package bricker;

/**
 * shared counter of how many lives remaining in the game.
 */
public class LivesCounter {
	private int value;
	public LivesCounter(int initialValue){
		this.value = initialValue;
	}


	public int getValue() {
		return value;
	}
	public void increment(){
		value++;
	}
	public void decrement(){
		value--;
	}
}

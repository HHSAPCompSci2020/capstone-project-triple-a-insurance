
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;
/**
 * Notes: this class is used to set the time for each level,and it also sets how fast the time decreases
 * @author Aayush
 *
 */
public class LevelTimer {
	private int timeLeft;
	
	Timer currentTimeLeft = new Timer(120, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(timeLeft > 0) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				timeLeft = timeLeft - 1;
			}
			
		}
		
	});
	
	
	public LevelTimer(int timeLeft) {
		this.timeLeft = timeLeft;	
	}
	
	/**
	 * Starts the timer
	 */
	public void startTimer() {

		currentTimeLeft.start();		

	}
	/**
	 * Restarts timer with the new time
	 * @param timeLeft the new time
	 */
	public void restartTimer(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	/**
	 * Stops the timer
	 */
	public void stopTimer() {
		
		currentTimeLeft.stop();
		
	}
	
	/**
	 * Returns the time left in the timer
	 * @return the time left in the timer
	 */
	public int getTimeLeft() {
		return timeLeft;
	}
	
	/**
	 * Returns the amount of zeros to make sure the time is displayed in 3 digits at all times
	 * @return the amount of zeros to make the length 3
	 */
	public String timeStartingZeros() {
		int tl = 3 - Integer.toString(timeLeft).length();
		String displayTime = "";
		for(int i = 0;i < tl;i++) {
			displayTime += "0";
		}
		return displayTime;
	}
	
	/**
	 * Decreases the time left on the timer by one
	 */
	public void decreaseTime() {
		timeLeft--;
	}

}

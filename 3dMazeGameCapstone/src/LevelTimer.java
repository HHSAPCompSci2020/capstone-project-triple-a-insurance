
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
/**
 * Notes: this class is used to set the time for each level,and it also sets how fast the time decreases
 * @author Aayush
 *
 */
public class LevelTimer {
	private int timeLeft;
	
	Timer currentTimeLeft = new Timer(timeLeft, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(timeLeft > 0) {
				timeLeft = timeLeft - 1;
			}
			
		}
		
	});
	
	
	public LevelTimer(int timeLeft) {
		this.timeLeft = timeLeft;	
	}
	
	public void startTimer() {

		currentTimeLeft.start();		

	}
	public void restartTimer(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	public void stopTimer() {
		
		currentTimeLeft.stop();
		
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}
	
	public String timeStartingZeros() {
		int tl = 3 - Integer.toString(timeLeft).length();
		String displayTime = "";
		for(int i = 0;i < tl;i++) {
			displayTime += "0";
		}
		return displayTime;
	}
	
	public void decreaseTime() {
		timeLeft--;
	}

}

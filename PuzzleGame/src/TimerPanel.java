import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TimerPanel extends JPanel  implements ActionListener {
	boolean timer_started = false;
	static final int timer_panel_widht = 400;
	static final int timer_panel_height = 50;
	JLabel timeLabel =new JLabel();
	JLabel winLabel = new JLabel();
	
	int elapsedTime = 0;
	int seconds = 0;
	int minutes = 0;
	int hours = 0;
	String seconds_string= String.format("%02d", seconds);
	String minutes_string= String.format("%02d", minutes);
	String hours_string= String.format("%02d", hours);
	Timer timer = new Timer(1000,new ActionListener() {   
		public void actionPerformed(ActionEvent e) {
			if(timer_started==true) {
			elapsedTime+=1000;
			hours = (elapsedTime/3600000);
			minutes = (elapsedTime/60000) % 60;
			seconds = (elapsedTime/1000) % 60;
			String seconds_string= String.format("%02d", seconds);
			String minutes_string= String.format("%02d", minutes);
			String hours_string= String.format("%02d", hours);
			timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
			}
		}
	});
	
	TimerPanel(){
		
		timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
		timeLabel.setForeground(Color.red);
		timeLabel.setFont(new Font ("Verdana",Font.PLAIN,20));
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		
		winLabel.setForeground(Color.red);
		winLabel.setFont(new Font ("Verdana",Font.PLAIN,20));
		winLabel.setHorizontalAlignment(JLabel.CENTER);
	winLabel.setText("Moves: ");
		
		this.add(timeLabel);
		this.add(winLabel);
		this.setPreferredSize(new Dimension(timer_panel_widht,timer_panel_height));
		this.setLayout(new GridLayout(1,3));
		this.setBackground(Color.black);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	void startTimer() {
		
	timer_started=true;	
	 timer.start();
		  
		
	}
	void stopTimer() {
		
		
		timer.stop();
	}

}

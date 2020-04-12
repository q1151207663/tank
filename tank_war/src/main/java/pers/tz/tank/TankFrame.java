package pers.tz.tank;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TankFrame extends Frame {
	
	public TankFrame(){
		setSize(800 ,600);
		setResizable(false);
		setTitle("tank war");
		setVisible(true);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	
	
}

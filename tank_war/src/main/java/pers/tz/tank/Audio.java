
package pers.tz.tank;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Audio extends Thread {
	
	private SourceDataLine sourceDataLine = null;
	private AudioInputStream audioInputStream = null;
	private AudioFormat audioFormat = null;
	private DataLine.Info dataLine_info = null;
	
	@Override
	public void run() {
		try {
			byte[] bs = new byte[1024];
			int len = 0;
			sourceDataLine.open(audioFormat ,1024);
			sourceDataLine.start();
			while((len = audioInputStream.read(bs)) > 0) {
				sourceDataLine.write(bs, 0, len);
			}
			audioInputStream.close();
			sourceDataLine.drain();
			sourceDataLine.close();
			
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Audio(String fileName) {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(Audio.class.getClassLoader().getResource(fileName));
			audioFormat = audioInputStream.getFormat();
			dataLine_info = new DataLine.Info(SourceDataLine.class, audioFormat);
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLine_info);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}

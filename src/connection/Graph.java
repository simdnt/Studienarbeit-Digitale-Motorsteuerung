package connection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import waitLib.Wait;

import javax.swing.JComponent;

public class Graph extends JComponent implements ActionListener{
	private static final long serialVersionUID = 8245471415067104760L;
	private boolean[] mValuesOverTime = new boolean[600];
	private boolean[] mConnecOverTime = new boolean[600];
	boolean mCurrentValue = false;
	Timer mTimer;

	public Graph(){
		mTimer = new javax.swing.Timer(Wait.Short*3, this);
		mTimer.start();
	}

	@Override
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		for (int i = 0; i < mValuesOverTime.length; i++) {
			g.fillRect(i, mValuesOverTime[i]?10:30, 1, mValuesOverTime[i]?20:1);
		}
		for (int i = 0; i < mConnecOverTime.length; i++) {
			boolean big = mConnecOverTime[i]&&mValuesOverTime[i];
			g.fillRect(i, big?40:60, 1, big?20:1);
		}
	}
	void setValue(boolean aValue){
		mCurrentValue=aValue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < mValuesOverTime.length-1; i++) {
			mValuesOverTime[i]=mValuesOverTime[i+1];
		}
		mValuesOverTime[mValuesOverTime.length-1]=mCurrentValue;

		for (int i = 0; i < mConnecOverTime.length-1; i++) {
			mConnecOverTime[i]=mConnecOverTime[i+1];
		}
		mConnecOverTime[mConnecOverTime.length-1]=Wire.IsConnected();
		repaint();
	}


}

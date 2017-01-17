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
	private boolean[] mSenderValuessOverTime = new boolean[600];
	private boolean[] mReceiverValuesOverTime = new boolean[600];
	Timer mTimer;

	public Graph(){
		mTimer = new javax.swing.Timer(Wait.Short*3, this);
		mTimer.start();
	}

	@Override
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		for (int i = 0; i < mSenderValuessOverTime.length; i++) {
			g.fillRect(i, mSenderValuessOverTime[i]?10:30, 1, mSenderValuessOverTime[i]?20:1);
		}
		for (int i = 0; i < mReceiverValuesOverTime.length; i++) {
			g.fillRect(i, mReceiverValuesOverTime[i]?40:60, 1, mReceiverValuesOverTime[i]?20:1);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < mSenderValuessOverTime.length-1; i++) {
			mSenderValuessOverTime[i]=mSenderValuessOverTime[i+1];
		}
		mSenderValuessOverTime[mSenderValuessOverTime.length-1]=Wire.SenderValue();

		for (int i = 0; i < mReceiverValuesOverTime.length-1; i++) {
			mReceiverValuesOverTime[i]=mReceiverValuesOverTime[i+1];
		}
		mReceiverValuesOverTime[mReceiverValuesOverTime.length-1]=Wire.ReceiverValue();
		repaint();
	}


}

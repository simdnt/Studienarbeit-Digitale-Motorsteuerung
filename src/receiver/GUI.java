package receiver;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class GUI extends JFrame{

	private static final long serialVersionUID = 89114179729493503L;
	private JSlider mSlider;
	private JLabel  mLabel;
	private boolean connected = false;

	public GUI(){
		super("Receiver");
		this.setBounds(0,100,600,100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		this.setVisible(true);
	}

	private void initComponents() {
		this.setLayout(new GridLayout(2, 1));

		mLabel = new JLabel("Der empfangene Wert ist: unbestimmt");
		this.getContentPane().add(mLabel);

		mSlider = new JSlider(0, 255, 123);
		mSlider.setEnabled(false);
		this.getContentPane().add(mSlider);
	}

	public void received(int aValue){
		if(aValue==-1){
			if(connected){
				connected = false;
				mLabel.setText("Verbindung unterbrochen");
				mSlider.setValue(0);
			}
		}else{
			connected = true;
			mLabel.setText("Der empfangene Wert ist: "+aValue);
			mSlider.setValue(aValue);
		}
	}
}

package sender;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI extends JFrame{

	private static final long serialVersionUID = 8683274222810244425L;
	private JSlider mSlider;
	private JLabel  mLabel;
	
	public GUI(){
		super("Sender");
		setBounds(0, 0, 600, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		setVisible(true);
	}
	private void initComponents() {
		this.setLayout(new GridLayout(2, 1));
		
		mLabel = new JLabel("Stellen sie den zu übertragenden Wert ein: 123");
		this.getContentPane().add(mLabel);
		
		mSlider = new JSlider(0, 255, 123);
		mSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				mLabel.setText("Stellen sie den zu übertragenden Wert ein: "+mSlider.getValue());
			}
		});
		this.getContentPane().add(mSlider);
	}
	
	public int getSliderValue(){
		return mSlider.getValue();
	}

}

package microcontroller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import microcontroller.MicroController.ReceiverController;
import microcontroller.MicroController.SenderController;

public abstract class GUI extends JFrame{
	
	private static final long serialVersionUID = 4489018385439921538L;
	protected JSlider mSlider;
	protected JLabel  mLabel;
	protected JButton mButtonStart, mButtonStop;
	protected MicroController mMC;
	

	public GUI(){
		super("Sender");
		setBounds(0, 0, 600, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		setVisible(true);
	}
	private void initComponents() {
		this.setLayout(new BorderLayout());
		JPanel lPanelCenter = new JPanel(new GridLayout(2,1));
		lPanelCenter.add(mLabel = new JLabel());
		lPanelCenter.add(mSlider= new JSlider(0, 255, 123));
		this.getContentPane().add(lPanelCenter,BorderLayout.CENTER);
		JPanel lPanelEast = new JPanel(new GridLayout(2,1));
		lPanelEast.add(mButtonStart = new JButton("Restart"));
		lPanelEast.add(mButtonStop = new JButton("Stop"));
		mButtonStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mMC!=null)
					mMC.reset();
			}
		});
		mButtonStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mMC!=null)
					mMC.turnOff();
			}
		});
		this.getContentPane().add(lPanelEast,BorderLayout.EAST);
	}
	
	public static final class SenderGUI extends GUI{
		private static final long serialVersionUID = -6233383830213010970L;
		private SenderController mC(){return (SenderController)mMC;}
		public SenderGUI(){
			super();
			mLabel.setText("Stellen Sie den zu uebertragenden Wert ein: 123");
			mSlider.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					mLabel.setText("Stellen Sie den zu uebertragenden Wert ein: "+mSlider.getValue());
					if(mMC!=null)
						mC().setValueToSend(mSlider.getValue());
				}
			});
			mMC = new SenderController();
			mC().setValueToSend(mSlider.getValue());
		}
	}
	public static final class ReceiverGUI extends GUI implements ActionListener{
		private static final long serialVersionUID = -6278927550656335963L;

		private ReceiverController mC(){return (ReceiverController)mMC;}
		private Timer tm = new Timer(5, this);
		
		public ReceiverGUI(){
			super();
			this.setLocation(0, 100);
			mLabel.setText("Der empfangene Wert ist: unbestimmt");
			mSlider.setEnabled(false);
			mMC = new ReceiverController();
			tm.start();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			mLabel.setText("Der empfangene Wert ist: "+mC().getReceivedValue());
			mSlider.setValue(mC().getReceivedValue());
		}
		
	}
}

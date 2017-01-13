package connection;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class GUI extends JFrame{
	private static final long serialVersionUID = 1537498402992201313L;
	private JButton mButtonConnect, mButtonDisconnect;
	private JTextArea mStatusINFO;
	
	public GUI(){
		super("ConnectionManager");
		this.setBounds(0, 200, 600, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initcomponents();
		this.setVisible(true);
	}

	private void initcomponents() {
		this.setLayout(new BorderLayout(10,10));
		JPanel p = new JPanel(new GridLayout(1,2));
		this.getContentPane().add(p,BorderLayout.CENTER);
		
		mButtonConnect=new JButton("Connect");
		mButtonConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Wire.setState(true);
				setStatusText(true);
			}
		});
		p.add(mButtonConnect);
		
		mButtonDisconnect=new JButton("Disconnect");
		mButtonDisconnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Wire.setState(false);
				setStatusText(false);
			}
		});
		p.add(mButtonDisconnect);
		
		mStatusINFO=new JTextArea();
		mStatusINFO.setEditable(false);
		Wire.setState();
		setStatusText(Wire.state==Wire.Connected);
		this.getContentPane().add(mStatusINFO,BorderLayout.SOUTH);
	
	}
	private void setStatusText(boolean aIsConnected){
		if(aIsConnected)
			mStatusINFO.setText("Currently the Wire is connected");
		else
			mStatusINFO.setText("Currently the Wire is disconnected");
	}
}

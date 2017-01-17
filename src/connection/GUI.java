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
	private JTextArea mStatusINFO;
	
	public GUI(){
		super("ConnectionManager");
		this.setBounds(0, 200, 600, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initcomponents();
		this.setVisible(true);
	}

	private void initcomponents() {
		this.setLayout(new GridLayout(2,1));
		JPanel lPanelTopHalf = new JPanel(new BorderLayout(10,10));
		JPanel p = new JPanel(new GridLayout(1,2));
		lPanelTopHalf.add(p,BorderLayout.CENTER);
		
		JButton lButtonConnect=new JButton("Connect");
		lButtonConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Wire.setState(true);
				setStatusText(true);
			}
		});
		p.add(lButtonConnect);
		
		JButton lButtonDisconnect=new JButton("Disconnect");
		lButtonDisconnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Wire.setState(false);
				setStatusText(false);
			}
		});
		p.add(lButtonDisconnect);
		
		mStatusINFO=new JTextArea();
		mStatusINFO.setEditable(false);
		setStatusText(Wire.IsConnected());
		lPanelTopHalf.add(mStatusINFO,BorderLayout.SOUTH);

		this.getContentPane().add(lPanelTopHalf);
		
		this.getContentPane().add(new Graph());
	}
	private void setStatusText(boolean aIsConnected){
		if(aIsConnected)
			mStatusINFO.setText("Currently the Wire is connected");
		else
			mStatusINFO.setText("Currently the Wire is disconnected");
	}

}

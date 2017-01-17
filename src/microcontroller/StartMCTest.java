package microcontroller;

public class StartMCTest {

	public static void main(String[] args) {
		new GUI.SenderGUI();
		new GUI.ReceiverGUI();
		connection.StartConnectionManager.main(null);
	}

}

package sender;

import connection.Wire;
import waitLib.Wait;

//Sender Worker
public class Worker implements Runnable{

	//Sender GUI
	private GUI mGUI;
	
	public Worker(GUI aGUI){
		mGUI=aGUI;
	}

	@Override
	public void run() {
		while(true){
			int ValueToSend = mGUI.getSliderValue();
			System.out.println("Value sent: "+ValueToSend);
			sendTransmissionBeginns();
			for (int i = 0; i < 8; i++) {
				sendDigit(ValueToSend%2==1);
				ValueToSend/=2;
			}
			sendDigit(false);//Falls letztes Digit 1 war muss Änderung erfolgen befor TransmissionBeginns!
		}
	}

	private void sendDigit(boolean aDigit) {
		Wire.setValue(!aDigit);
		Wait.Full();
		Wire.setValue(aDigit);
		Wait.Full();
	}
	
	//Sendet 5 High Einheiten als STX
	private void sendTransmissionBeginns() {
		Wire.setValue(true);
		for (int i = 0; i < 5; i++)
			Wait.Full();
		Wire.setValue(false);
		Wait.Half();
	}
}

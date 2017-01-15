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
			sendTransmissionBeginns();
			sendDigits(ValueToSend);
		}
	}
	private void sendDigits(int aValue){
		for (int i = 0; i < 8; i++) {
			sendDigit(aValue%2==1);
			aValue/=2;
		}
	}
	private void sendDigit(boolean aDigit) {
		Wire.setValue(aDigit);
		Wait.Full();
	}
	
	//Sendet 15 High Einheiten als STX
	private void sendTransmissionBeginns() {
		Wire.setValue(false);
		Wait.Full();
		Wait.Full();
		Wire.setValue(true);
		for (int i = 0; i < 20; i++)
			Wait.Full();
		Wire.setValue(false);
		Wait.Half();
	}
}

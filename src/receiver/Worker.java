package receiver;

import connection.Wire;
import waitLib.Wait;

//Receiver Worker
public class Worker implements Runnable{

	//Receiver GUI
	private GUI mGUI;
	private boolean[] digits = new boolean[8];
	
	public Worker(GUI aGUI){
		mGUI=aGUI;
	}

	@Override
	public void run() {
		while(true){
			reconTransmissionBeginning();
			reconDigits();
			mGUI.received(digitsToInt());
		}
	}

	private int digitsToInt() {
		int ret = 0;
		int zweiHochi = 1;
		for (int i = 0; i < digits.length; i++) {
			if(digits[i])
				ret+=zweiHochi;
			zweiHochi*=2;
		}
		return ret;
	}

	//schreibt gelesene Werte in digits und gibt zuruek ob der gesendete Wert vollständig gelesen werden konnte
	private void reconDigits() {
		for (int i = 0; i < digits.length; i++) {
			Wait.Full();
			digits[i]=Wire.ReceiverValue();
		}
	}

	private void reconTransmissionBeginning() {
		int highCount=0, lowCount=0;
		while(true){
			if(Wire.ReceiverValue()){
				highCount++;
				lowCount=0;
			}else{
				lowCount++;
				highCount=0;
			}
			Wait.Full();
			if(highCount>18){
				while(Wire.ReceiverValue())
					Wait.Short();
				return;
			}
			if(lowCount>11){
				mGUI.received(-1);
			}
		}
	}
}
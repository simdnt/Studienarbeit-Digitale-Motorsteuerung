package receiver;

import connection.Wire;
import waitLib.Wait;

//Receiver Worker
public class Worker implements Runnable{

	//Receiver GUI
	private GUI mGUI;
	private boolean[] digits = new boolean[16];
	
	public Worker(GUI aGUI){
		mGUI=aGUI;
	}

	@Override
	public void run() {
		while(true){
			reconTransmissionBeginning();
//			System.out.println("Recognition Succeeded!");
			if(reconDigits()){//Bits erfolgreich erhalten
				System.out.println("GotDigits");
				mGUI.received(digitsToInt());
			}else{//Entweder Synchronisationsfehler oder Leitung getrennt
				mGUI.received(-1);
			}
		}
	}

	private int digitsToInt() {
		int ret = 0;
		int zweiHochi = 1;
		for (int i = 1; i < digits.length; i+=2) {
			if(digits[i])
				ret+=zweiHochi;
			zweiHochi*=2;
		}
		return 255-ret;
	}

	//schreibt gelesene Werte in digits und gibt zurück ob der gesendete Wert vollständig gelesen werden konnte
	private boolean reconDigits() {
		Wait.Full();
		boolean last = Wire.Value();
		for (int i = 0; i < 16; i++) {
			int time=0;
			while(true){
				boolean b = Wire.Value();
				if(b!=last){
//					System.out.println(last+" time: "+time);
					last=b;
					break;
				}
				Wait.Short();
				time++;
				if(time>35)
					return false;
			}
			digits[i] = Wire.Value();
			if(time>15 && i<15)//Zwei Gleiche
				digits[++i] = Wire.Value();
		}
		return true;
	}

	private void reconTransmissionBeginning() {
		getHigh();
		int highCount=0, lowCount=0;
		while(true){
			if(Wire.Value()){
				highCount++;
				lowCount=0;
			}else{
				lowCount++;
				highCount=0;
			}
			Wait.Short();
			if(highCount>45){
				while(Wire.Value())
					Wait.Short();
				return;
			}
			if(lowCount>20){
				mGUI.received(-1);
				getHigh();
			}
		}
	}
	private void getHigh() {
		int lowCount = 0;
		while(true){
			if(Wire.Value())
				return;
			else
				lowCount++;
			if(lowCount>20)
				mGUI.received(-1);
			Wait.Short();
		}
	}
}
package connection;

public class Wire {

	private static boolean sValue;
	private static boolean sIsConnected = false;
	
	public static boolean ReceiverValue(){
		if(sIsConnected)
			return SenderValue();
		else
			return false;
	}
	public static boolean SenderValue(){
		return sValue;
	}
	public static void setValue(boolean aValue){
		sValue=aValue;
	}
	static boolean IsConnected(){
		return sIsConnected;
	}
	static void setState(boolean aIsConnected){
		sIsConnected=aIsConnected;
	}
}

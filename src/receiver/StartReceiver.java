package receiver;

public class StartReceiver {
	
	public static void main(String[] args){
		new Thread(new Worker(new GUI())).start();
	}
	
}

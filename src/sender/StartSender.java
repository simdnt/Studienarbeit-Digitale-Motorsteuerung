package sender;

public class StartSender {

	public static void main(String[] args){
		new Thread(new Worker(new GUI())).start();
	}
	
}

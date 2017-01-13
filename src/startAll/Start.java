package startAll;

public class Start {

	public static void main(String[] args) {
		sender.StartSender.main(null);
		receiver.StartReceiver.main(null);
		connection.StartConnectionManager.main(null);
	}

}

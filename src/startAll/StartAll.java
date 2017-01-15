package startAll;

public class StartAll {

	public static void main(String[] args) {
		sender.StartSender.main(null);
		receiver.StartReceiver.main(null);
		connection.StartConnectionManager.main(null);
	}
}

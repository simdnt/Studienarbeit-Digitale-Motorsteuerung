package waitLib;

public class Wait {

	public static final int Full =100,
							Half =50,
							Short= 5;
	
	public static void Full(){
		try {
			Thread.sleep(Full);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void Half(){
		try {
			Thread.sleep(Half);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void Short(){
		try {
			Thread.sleep(Short);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

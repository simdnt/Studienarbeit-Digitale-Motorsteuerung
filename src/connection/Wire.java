package connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Wire {

	private static File ValueFile,ConnectionStateFile;
	static ConnectionStatus state;
	static final ConnectionStatus Connected = new StateConnected(),
			DisConnected = new StateDisConnected();
	public static boolean Value(){
		setState();
		return state.getValue();
	}
	public static void setValue(boolean aValue){
		try {
			assureValueFile();
			FileOutputStream out = new FileOutputStream(ValueFile,false);
			out.write(aValue?1:0);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static FileInputStream getReader() {
		try {
			assureValueFile();
			return new FileInputStream(ValueFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	private static void assureValueFile() {
		try{
			if(ValueFile==null)
				ValueFile=new File("WireValue");
			if(!ValueFile.exists()){
				ValueFile.createNewFile();
				FileOutputStream out = new FileOutputStream(ValueFile);
				out.write(0);
				out.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void assureConnectionStateFile() {
		try{
			if(ConnectionStateFile==null)
				ConnectionStateFile=new File("ConnectionState");
			if(!ConnectionStateFile.exists()){
				ConnectionStateFile.createNewFile();
				FileOutputStream out = new FileOutputStream(ConnectionStateFile);
				out.write(0);
				out.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	static void setState() {
		try{
			assureConnectionStateFile();
			FileInputStream in = new FileInputStream(ConnectionStateFile);
			state = in.read()==1?Connected:DisConnected;
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	static void setState(boolean aIsConnected){
		try{
			assureConnectionStateFile();
			FileOutputStream out = new FileOutputStream(ConnectionStateFile,false);
			out.write(aIsConnected?1:0);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

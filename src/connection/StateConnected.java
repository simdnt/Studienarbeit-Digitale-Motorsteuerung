package connection;

import java.io.FileInputStream;

class StateConnected implements ConnectionStatus{
	
	@Override
	public boolean getValue() {
		try{
			FileInputStream in = Wire.getReader();
			boolean ret = in.read()==1;
			in.close();
			return ret;
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
			return false;
		}
	}

}

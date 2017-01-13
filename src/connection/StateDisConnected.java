package connection;

class StateDisConnected implements ConnectionStatus{

	@Override
	public boolean getValue() {
		return false;
	}

}

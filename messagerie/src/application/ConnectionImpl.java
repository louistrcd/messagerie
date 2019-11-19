package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConnectionImpl extends UnicastRemoteObject implements Connection {

	protected ConnectionImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialogue connect(String pseudo) {
		return null;
	}

	@Override
	public void disconnect(String pseudo) {
	
	}

}

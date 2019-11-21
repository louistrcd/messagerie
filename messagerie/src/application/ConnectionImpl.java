package application;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ConnectionImpl extends UnicastRemoteObject implements Connection {

	protected ConnectionImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Emitter connect(String pseudo, Receiver rcv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect(String pseudo) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

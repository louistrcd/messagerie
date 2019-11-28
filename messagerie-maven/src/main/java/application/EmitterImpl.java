package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EmitterImpl extends UnicastRemoteObject implements Emitter{
	
	String pseudo;
	Connection myConnection;
	
	public EmitterImpl(String pseudo) throws RemoteException {
		super();
		this.pseudo = pseudo;
	}
	
	@Override
	public void setMyConnection(Connection c) {
		this.myConnection = c;
	}

	@Override
	public void sendMessage(String to, String text) throws RemoteException {
		Receiver  r = myConnection.getReceiverOf(to);
		if(!(r==null)) {
			r.receive(pseudo, text);
		}
	}

}

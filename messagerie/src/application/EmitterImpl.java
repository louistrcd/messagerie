package application;

import java.rmi.RemoteException;

public class EmitterImpl implements Emitter{
	
	String pseudo;
	Connection myConnection;
	
	public EmitterImpl(String pseudo) {
		this.pseudo = pseudo;
	}
	
	@Override
	public void setMyConnection(Connection c) {
		myConnection = c;
	}

	@Override
	public void sendMessage(String to, String text) throws RemoteException {
		Receiver  r = myConnection.getReceiverOf(to);
		if(!(r==null)) {
			r.receive(pseudo, text);
		}
	}

}

package application;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogueImpl extends UnicastRemoteObject implements Dialogue{

	String pseudo;
	
	protected DialogueImpl() throws RemoteException {
		super();
	}
	
	protected DialogueImpl(String pseudo) throws RemoteException {
		super();
		this.pseudo = pseudo;
	}

	@Override
	public List<String> getClients(Connection myConnection) throws RemoteException {
		return myConnection.getClients();
	}

	@Override
	public void sendMessage(String from, String to, String text, Connection myConnection) throws RemoteException {
		myConnection.sendMessage(from, to, text);
	}

	@Override
	public List<String> getMessages(String pseudo, Connection myConnection) throws RemoteException {
		return myConnection.getMessages(pseudo);
	}

}
 
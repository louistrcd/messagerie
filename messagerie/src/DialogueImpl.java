import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DialogueImpl extends UnicastRemoteObject implements Dialogue{

	List<String> clients = new ArrayList<>();
	
	protected DialogueImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void connect(String pseudo) throws RemoteException {
		if(!clients.contains(pseudo)) {
			clients.add(pseudo);
		}
	}

	@Override
	public void disconnect(String pseudo) throws RemoteException {
		if(clients.contains(pseudo)) {
			clients.remove(pseudo);
		}
	}

	@Override
	public String[] getClients() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String nom, String from, String to) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getMessages(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
 
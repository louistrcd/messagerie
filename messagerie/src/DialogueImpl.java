import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DialogueImpl extends UnicastRemoteObject implements Dialogue{

	List<String> clients = new ArrayList<>();
	List<Message> messages = new ArrayList<>();
	
	protected DialogueImpl() throws RemoteException {
		super();
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
	public List<String> getClients() throws RemoteException {
		return clients;
	}

	@Override
	public void sendMessage(String from, String to, String message) throws RemoteException {
		Message newMessage = new Message(from, to, message);
		messages.add(newMessage);
	}

	@Override
	public List<String> getMessages(String pseudo) throws RemoteException {
		List<String> result = new ArrayList<>();
		for(Message m : messages) {
			if(m.to.contentEquals(pseudo)) {
				result.add(m.from + " : " + m.message);
			}
		}
		return result;
	}

}
 
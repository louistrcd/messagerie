package application;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogueImpl extends UnicastRemoteObject implements Dialogue{

	List<String> clients = new ArrayList<>();
	HashMap<String, ArrayList<Message>> mailbox = new HashMap<String, ArrayList<Message>>();
	
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
	public List<String> getClients() throws RemoteException {
		return clients;
	}

	@Override
	public void sendMessage(String from, String to, String text) throws RemoteException {
		Message message = new Message(from, text);	
		if(mailbox.get(to)!=null) {
			mailbox.get(to).add(message);
		}else {
			mailbox.put(to, new ArrayList<Message>());
			mailbox.get(to).add(message);
		}
	}

	@Override
	public List<String> getMessages(String pseudo) throws RemoteException {
		List<String> messages = new ArrayList<String>();
		if(mailbox.get(pseudo)==null) {
			messages.add("No message in the mailbox");
		}else {
			for(Message m : mailbox.get(pseudo)) {
				messages.add(m.showMessage());
			}
		}
		return messages;
	}

}
 
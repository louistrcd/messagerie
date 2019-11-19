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
	
	HashMap<String, DialogueImpl> clients = new HashMap<String, DialogueImpl>();
	HashMap<String, ArrayList<Message>> mailbox = new HashMap<String, ArrayList<Message>>();
	
	protected ConnectionImpl() throws RemoteException {
		super();
	}

	@Override
	public Dialogue connect(String pseudo) throws RemoteException {
		DialogueImpl dialogue = null;
		if(!clients.containsValue(pseudo)) {
			dialogue = new DialogueImpl(pseudo);
			clients.put(pseudo, dialogue);
		}
		return dialogue;
	}

	@Override
	public void disconnect(String pseudo) {
		clients.remove(pseudo);
	}

	@Override
	public List<String> getClients() {
		List<String> allPseudos = new ArrayList<String>();
		Set<String> keys = clients.keySet();
		for(String e : keys) {
			allPseudos.add(e);
		}
		return allPseudos;
	}

	@Override
	public void sendMessage(String from, String to, String text) {
		Message message = new Message(from, text);
		if(mailbox.get(to)!=null) {
			mailbox.get(to).add(message);
		}else {
			mailbox.put(to, new ArrayList<Message>());
			mailbox.get(to).add(message);
		}
	}
	
	public List<String> getMessages(String pseudo) {
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

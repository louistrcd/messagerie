package application;

import java.util.ArrayList;
import java.util.List;

public class ReceiverImpl implements Receiver {
	
	List<String> connected = new ArrayList<String>();
	List<String> messages = new ArrayList<String>();
	
	public ReceiverImpl(List<String> clients) {
		this.connected = clients;
	}
	
	@Override
	public List<String> getMessages(){
		return messages;
	}
	
	@Override
	public void receive(String from, String text) {
		messages.add(from + " : " + text);
		
	}

	@Override
	public void initClients(List<String> Clients) {
		connected = Clients;
		
	}

	@Override
	public void addClient(String client) {
		connected.add(client);
		
	}

	@Override
	public void remClient(String client) {
		connected.remove(client);
		
	}

}

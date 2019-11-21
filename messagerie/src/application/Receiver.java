package application;

import java.util.List;

public interface Receiver {

	void receive(String from, String text);
	void initClients(List<String> Clients);
	void addClient(String client);
	void remClient(String client);
}

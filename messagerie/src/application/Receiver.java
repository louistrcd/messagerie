package application;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Receiver extends Remote {

	List<String> getMessages();
	void receive(String from, String text) throws RemoteException;
	void initClients(List<String> Clients) throws RemoteException;
	void addClient(String client) throws RemoteException;
	void remClient(String client) throws RemoteException;
}

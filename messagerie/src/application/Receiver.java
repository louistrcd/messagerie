package application;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import javafx.collections.ObservableList;

public interface Receiver extends Remote {
	void setController(ControllerGUI controller) throws RemoteException;
	ObservableList<String> getMessages() throws RemoteException;
	ObservableList<String> getClients() throws RemoteException;
	ObservableList<String> getMailbox(String pseudo) throws RemoteException;
	void receive(String from, String text) throws RemoteException;
	void initClients(List<String> Clients) throws RemoteException;
	void addClient(String client) throws RemoteException;
	void remClient(String client) throws RemoteException;
}

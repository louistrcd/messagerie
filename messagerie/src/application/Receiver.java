package application;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import javafx.collections.ObservableList;

public interface Receiver extends Remote {
	void initMailbox(String client) throws RemoteException;
	ObservableList<String> getClients() throws RemoteException;
	ObservableList<String> getMailbox(String pseudo) throws RemoteException;
	HashMap<String, ObservableList<String>> getMailbox() throws RemoteException;
	void receive(String from, String text) throws RemoteException;
	void initClients(List<String> Clients) throws RemoteException;
}

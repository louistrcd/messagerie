package application;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Connection extends Remote{
	Dialogue connect(String pseudo) throws RemoteException;
	void disconnect(String pseudo)throws RemoteException;
	List<String> getClients()throws RemoteException;
	void sendMessage(String from, String to, String text)throws RemoteException;
	List<String> getMessages(String pseudo)throws RemoteException;
}

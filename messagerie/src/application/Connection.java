package application;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Connection extends Remote{
	List<String> getConnected() throws RemoteException;
	Receiver getReceiverOf(String pseudo) throws RemoteException;
	Emitter connect(String pseudo, Receiver rcv) throws RemoteException;
	void disconnect(String pseudo) throws RemoteException;
}

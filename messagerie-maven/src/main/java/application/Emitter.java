package application;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Emitter extends Remote{
	
	void setMyConnection(Connection c)  throws RemoteException;
	void sendMessage(String to, String text) throws RemoteException;

}

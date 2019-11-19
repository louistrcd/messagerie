package application;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Dialogue extends Remote{
	List<String> getClients(Connection myConnection) throws RemoteException;
	void sendMessage(String from, String to, String message, Connection myConnection) throws RemoteException;
	List<String> getMessages(String pseudo, Connection myConnection) throws RemoteException;
}

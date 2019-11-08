import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Dialogue extends Remote{
	void connect(String pseudo) throws RemoteException;
	void disconnect(String pseudo) throws RemoteException;
	String[] getClients() throws RemoteException;
	void sendMessage(String nom, String from, String to) throws RemoteException;
	String[] getMessages(String pseudo) throws RemoteException;
}

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Dialogue extends Remote{
	void connect(String pseudo) throws RemoteException;
	void disconnect(String pseudo) throws RemoteException;
	List<String> getClients() throws RemoteException;
	void sendMessage(String from, String to, String message) throws RemoteException;
	List<String> getMessages(String pseudo) throws RemoteException;
}

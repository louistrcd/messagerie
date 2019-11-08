import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class DialogueImpl extends UnicastRemoteObject implements Dialogue{

	protected DialogueImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void connect(String pseudo) throws RemoteException {
		try {
			LocateRegistry.getRegistry(1099).bind(pseudo, this);
			System.out.println(LocateRegistry.getRegistry(1099).lookup(pseudo));
		} catch (AlreadyBoundException e) {
			System.out.println("Connexion fail");
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void disconnect(String pseudo) throws RemoteException {
		try {
			LocateRegistry.getRegistry(1099).unbind(pseudo);
		}catch(NotBoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String[] getClients() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String nom, String from, String to) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getMessages(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}

package application;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			ConnectionImpl myConnection = new ConnectionImpl();
			System.out.println(myConnection.getRef().remoteToString());
			Naming.rebind("Connection", myConnection);
			System.out.println("Serveur actif");
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}

}

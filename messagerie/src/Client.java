import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dialogue myComponent;
		try {
			// example of a RMI URL use to retrieve a remote reference
			myComponent = (Dialogue) Naming.lookup("rmi://localhost:1099/Dialogue");
			Scanner reader = new Scanner(System.in);
			System.out.println("Veuillez entrer votre pseudo ");
			String monPseudo = reader.next();
			reader.close();
			try {
				myComponent.connect(monPseudo);
				System.out.println("Client connecté");
				System.out.println(LocateRegistry.getRegistry(1099).lookup(monPseudo));
				System.out.println("Do you want to disconnect? O/N");
				Scanner r = new Scanner(System.in);
				String answer = r.next().toUpperCase();
				r.close();
				switch (answer) {
				case "O":
					myComponent.disconnect(monPseudo);
					System.out.println(monPseudo + " s'est déconnecté");
				}
			} catch (Exception e) {
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

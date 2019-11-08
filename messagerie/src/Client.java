import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
				System.out.println("Client connected");
				System.out.println("What do you want to do ? S : Send, R : Read, D : Disconnect, C : See clients");
				Scanner r = new Scanner(System.in);
				String answer = r.next().toUpperCase();
				r.close();
				switch (answer) {
				case "S":
					System.out.println("Who is the recipient ?");
					r = new Scanner(System.in);
					String to = r.next().toUpperCase();
					r.close();
					System.out.println("What is the message ?");
					r = new Scanner(System.in);
					String message = r.next().toUpperCase();
					r.close();
					myComponent.sendMessage(monPseudo, to, message);
				case "R":
					System.out.println(myComponent.getMessages(monPseudo));
				case "C":
					System.out.println(myComponent.getClients());
				case "D":
					myComponent.disconnect(monPseudo);
					System.out.println(monPseudo + " disconnected");
				}
			} catch (Exception e) {
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

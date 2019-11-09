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
			Scanner r = new Scanner(System.in);
			System.out.println("Please enter your pseudonym :");
			String myPseudo = r.next();
			try {
				myComponent.connect(myPseudo);
				System.out.println("You are connected as " + myPseudo);
				System.out.println("What do you want to do ? S : Send, R : Read, D : Disconnect, C : See clients");
				r = new Scanner(System.in);
				String answer = r.next().toUpperCase();
				while(!answer.equals("D")) {
					switch (answer) {
					case "S":
						System.out.println("Who is the recipient ?");
						r = new Scanner(System.in);
						String to = r.next();
						System.out.println("What is the message ?");
						r = new Scanner(System.in);
						String message = r.nextLine();
						myComponent.sendMessage(myPseudo, to, message);
						System.out.println("\u001B[32m" + "Your message was successfully sent to " + to + "\u001B[0m");
						break;
					case "R":
						System.out.println(myComponent.getMessages(myPseudo));
						break;
					case "C":
						System.out.println(myComponent.getClients());
						break;
					}
					System.out.println("What do you want to do ? S : Send, R : Read, D : Disconnect, C : See clients");
					r = new Scanner(System.in);
					answer = r.next().toUpperCase();
				}
				myComponent.disconnect(myPseudo);
				System.out.println("You are disconnected");
			} catch (Exception e) {
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

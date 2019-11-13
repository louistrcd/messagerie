package application;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application{
	
	static Dialogue myComponent;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		if (System.getSecurityManager() == null) {
//			System.setSecurityManager(new RMISecurityManager());
//			}
		launch(args);
		try {
			myComponent = (Dialogue) Naming.lookup("rmi://localhost:1099/Dialogue");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(Client.class.getResource("GUI.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
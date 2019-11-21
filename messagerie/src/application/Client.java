package application;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application{

	Connection myConnection;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		try {
			myConnection = (Connection) Naming.lookup("rmi://localhost/Connection");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(Client.class.getResource("GUI.fxml"));
			ControllerGUI controller = new ControllerGUI(myConnection);
			loader.setController(controller);
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(Client.class.getResource("../css/styleAnalyse.css").toExternalForm());
			stage.setScene(scene);
			stage.setOnCloseRequest(e->{
				try {
					controller.close();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			stage.setTitle("Messenger");
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
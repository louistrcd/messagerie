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
	
	Dialogue myComponent;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		try {
			myComponent = (Dialogue) Naming.lookup("rmi://localhost:1099/Dialogue");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(Client.class.getResource("GUI.fxml"));
			ControllerGUI controller = new ControllerGUI(myComponent);
			loader.setController(controller);
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(Client.class.getResource("styleAnalyse.css").toExternalForm());
			stage.setScene(scene);
			stage.setOnCloseRequest(e->controller.close());
			stage.setTitle("Messenger");
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
package application;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Client extends Application{

	Connection myConnection;
	Timeline start;
	public static String ipAdress = "localhost";
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Client.class.getResource("GUI.fxml"));
				ControllerGUI controller = new ControllerGUI();
				loader.setController(controller);
				Scene scene = new Scene(loader.load());
				scene.getStylesheets().add(Client.class.getResource("../css/styleAnalyse.css").toExternalForm());
				stage.setScene(scene);
				stage.setOnCloseRequest(e->{
					try {
						controller.close();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				});
				stage.setTitle("Messenger");
				stage.initStyle(StageStyle.DECORATED);
				stage.getIcons().add(new Image(new FileInputStream(Client.class.getResource("../css/messenger.png").getPath())));
				stage.setOpacity(0);
				stage.show();
				startAnimation(stage);
	}
	
	public void startAnimation(Stage stage) {		
        start = new Timeline(new KeyFrame(Duration.seconds(0.02), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
				if(stage.getOpacity()<1) {
					stage.setOpacity(stage.getOpacity()+0.05);
				}else {
					start.stop();
				}
            }
        }));
		start.setCycleCount(Timeline.INDEFINITE);
		start.play();
	}

}
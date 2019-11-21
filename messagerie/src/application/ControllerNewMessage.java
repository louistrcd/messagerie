package application;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class ControllerNewMessage implements Initializable{

	Connection myConnection;
	Emitter myEmitter;
	String pseudo;
	@FXML
	ComboBox recipient;
	@FXML
	TextArea message;
	
	public ControllerNewMessage(String pseudo, Connection myConnection, Emitter myEmitter) {
		this.pseudo = pseudo;
		this.myConnection = myConnection;
		this.myEmitter = myEmitter;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			initRecipients();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void initRecipients() throws RemoteException {
		if(recipient!=null) {
	        new AutoCompleteComboBoxListener<>(recipient);
		}
		for(String s : myConnection.getConnected()) {
			recipient.getItems().add(s);
		}
	}
	
	public void sendMessage() throws RemoteException {
		if(recipient.getEditor().getText().length()>3 && message.getText().length()>3) {
			myEmitter.sendMessage(recipient.getEditor().getText(), message.getText());
			Stage currentStage = (Stage) recipient.getScene().getWindow();
			PauseTransition pause = new PauseTransition(Duration.seconds(0.4));
			System.out.println("C'est fait");
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Your message has been sent to " + recipient.getEditor().getText());
			alert.setTitle("Message confirmation");
			alert.show();
			pause.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					currentStage.close();
				}
			});
			pause.play();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("No informations entered");
			alert.setTitle("Message or recipient error");
			alert.show();
		}
		

	}

}

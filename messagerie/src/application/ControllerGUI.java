package application;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;

public class ControllerGUI implements Initializable {

	Connection myConnection;
	Emitter myEmitter;
	Receiver myReceiver;

	@FXML
	TextField pseudo;
	@FXML
	Label labelPseudo;
	@FXML
	Label labelConnected;
	@FXML
	Label labelRecipient;
	@FXML
	HBox hboxConnect;
	@FXML
	BorderPane bpMessages;
	@FXML
	BorderPane paneActions;
	@FXML
	ListView<String> listClients;
	@FXML
	ListView<String> listMessages;
	@FXML
	TextArea message;
	ObservableList<String> myMessages = FXCollections.observableArrayList();

	public ControllerGUI(Connection myConnection) throws RemoteException {
		this.myConnection = myConnection;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public void initListView() throws RemoteException {
		listClients.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					String ligne = (String) listClients.getSelectionModel().getSelectedItem();
					if (ligne != null) {
						labelRecipient.setText(ligne);
						try {
							bpMessages.setVisible(true);
							initListMessages(ligne);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

	}
	
	public class YourFormatCell extends ListCell<String> {
	    @Override 
	    protected void updateItem(String item, boolean empty) {
	        super.updateItem(item, empty);
	        if(item!=null) {
	        	setText(item);
		        if(item.contains("You")) {
		        	setStyle("-fx-text-fill: white; -fx-background-color: #0099ff;");
		        }else if(item.contains("Bienvenue")) {
		        	setStyle("-fx-text-fill: #ffb700; -fx-background-color: #2e2e2e;");
		        }else {
		        	setStyle("-fx-text-fill: white; -fx-background-color: #2e2e2e;");
		        }
	        }

	    }
	}

	public void initListMessages(String recipient) throws RemoteException {
		listMessages.refresh();
		listMessages.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
		    @Override 
		    public ListCell<String> call(ListView<String> list) {
		        return new YourFormatCell();
		    }
		});
		listMessages.refresh();
		listMessages.setItems(myReceiver.getMailbox(recipient));
		listMessages.refresh();
	}

	public void connect() {
		if (!(pseudo.getText().length() <= 3)) {
			try {
				myReceiver = new ReceiverImpl();
				myReceiver.setController(this, pseudo.getText());
				myEmitter = myConnection.connect(pseudo.getText(), myReceiver);
				if (myEmitter == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Connection error");
					alert.setContentText("Pseudo already connected in a session");
					alert.show();
				} else {
					myEmitter.setMyConnection(myConnection);
					System.out.println("Your are connected as " + pseudo.getText());
					labelConnected.setVisible(true);
					labelPseudo.setText(pseudo.getText());
					pseudo.setText("");
					hboxConnect.setVisible(false);
					listClients.setItems(myReceiver.getClients());
					for(String s : myReceiver.getClients()) {
						myReceiver.initMailbox(s);
						System.out.println(s);
					}
					initListView();
					paneActions.setVisible(true);
				}

			} catch (RemoteException e) {
				e.printStackTrace();
			}

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("You must enter a valid name");
			alert.setTitle("Pseudonym error");
			alert.show();
		}
	}

	public void sendMessage() throws RemoteException {
		myEmitter.sendMessage(labelRecipient.getText(), message.getText());
		myReceiver.receive(labelRecipient.getText(), "You : " + message.getText());
		message.setText("");
	}

	public void disconnect() throws RemoteException {
		myConnection.disconnect(labelPseudo.getText());
		hboxConnect.setVisible(true);
		labelConnected.setVisible(false);
		labelPseudo.setText("");
		paneActions.setVisible(false);
		listMessages.getItems().clear();
		labelRecipient.setText("");
		System.out.println("Disconnected");
	}

	public void close() throws RemoteException {
		myConnection.disconnect(labelPseudo.getText());
		System.exit(0);
	}
	
	public String getPseudo() {
		return labelPseudo.getText();
	}

}

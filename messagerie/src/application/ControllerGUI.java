package application;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
	HBox hboxConnect;
	@FXML
	BorderPane paneActions;
	@FXML
	ListView<String> listMessages;
	@FXML
	TextArea detailMessage;

	public ControllerGUI(Connection myConnection) {
		this.myConnection = myConnection;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//refreshMessages();
	}

	public void initListView() {
		listMessages.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					String ligne = (String) listMessages.getSelectionModel().getSelectedItem();
					if (ligne != null) {
						detailMessage.setVisible(true);
						detailMessage.setText(ligne);
					}
				}
			}
		});
	}

	public void initListMessages() throws RemoteException {
		listMessages.getItems().clear();
		List<String> messages = myReceiver.getMessages();
		for (int i = messages.size() - 1; i >= 0; i--) {
			listMessages.getItems().add(messages.get(i));
		}
	}

	public void connect() {
		if (!(pseudo.getText().length() <= 3)) {
			try {
				myReceiver = myConnection.getReceiverOf(pseudo.getText());
				System.out.println(myReceiver.getMessages().size() + "taille messages");
				myEmitter = myConnection.connect(pseudo.getText(), myReceiver);
				if(myEmitter == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Connection error");
					alert.setContentText("Pseudo already connected in a session");
					alert.show();
				}else {
					myEmitter.setMyConnection(myConnection);
					System.out.println("Your are connected as " + pseudo.getText());
					labelConnected.setVisible(true);
					labelPseudo.setText(pseudo.getText());
					pseudo.setText("");
					hboxConnect.setVisible(false);
					initListView();
					initListMessages();
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

	public void showUsers() throws RemoteException {
		Timeline refreshUsers;
		ListView lv = new ListView();
		EventHandler e = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				List<String> clients;
				try {
					lv.getItems().clear();
					clients = myConnection.getConnected();
					for (String s : clients) {
						lv.getItems().add(s);
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		KeyFrame k = new KeyFrame(Duration.seconds(0.5), e);
		refreshUsers = new Timeline(k);
		refreshUsers.setCycleCount(Timeline.INDEFINITE);
		Stage stage = new Stage();
		BorderPane bp = new BorderPane();
		bp.setCenter(lv);
		Scene scene = new Scene(bp);
		scene.getStylesheets().add(Client.class.getResource("../css/styleAnalyse.css").toExternalForm());
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				refreshUsers.stop();
			}
		});
		stage.show();
		refreshUsers.play();
	}

	public void newMessage() {
		try {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			FXMLLoader loader = new FXMLLoader(Client.class.getResource("NewMessage.fxml"));
			ControllerNewMessage controller = new ControllerNewMessage(labelPseudo.getText(), myConnection, myEmitter);
			loader.setController(controller);
			Scene scene = new Scene(loader.load(), 500, 400);
			scene.getStylesheets().add(Client.class.getResource("../css/styleAnalyse.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("New message");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() throws RemoteException {
		myConnection.disconnect(labelPseudo.getText());
		hboxConnect.setVisible(true);
		labelConnected.setVisible(false);
		labelPseudo.setText("");
		paneActions.setVisible(false);
		listMessages.getItems().clear();
		detailMessage.setText("");
		detailMessage.setVisible(false);
		System.out.println("Disconnected");
	}

	public void close() throws RemoteException {
		myConnection.disconnect(labelPseudo.getText());
		System.exit(0);
	}

}

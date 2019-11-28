package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
	HBox hboxLabel;
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
	
	Timeline animationConnect;
	Timeline animationDisconnect;

	public ControllerGUI() throws RemoteException {
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addEnterActions();
	}

	public void addEnterActions() {
		pseudo.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					connect();
				}
			}
		});

		message.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					try {
						sendMessage();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					message.setText("");
				}
			}
		});

		message.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					message.setText("");
				}
			}
		});
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

	public class FormatCell extends ListCell<String> {
		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null) {
				setText(item);
				setPrefWidth(100);
				setWrapText(true);
				if (item.contains("You")) {
					setStyle("-fx-text-fill: white; -fx-background-color: #0099ff;");
				} else if (item.contains("Bienvenue")) {
					setStyle("-fx-text-fill: #ffb700; -fx-background-color: #2e2e2e;");
				} else {
					setStyle("-fx-text-fill: white; -fx-background-color: #2e2e2e;");
				}
			}
		}
	}

	public void initListMessages(String recipient) throws RemoteException {
		message.requestFocus();
		listMessages.refresh();
		listMessages.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new FormatCell();
			}
		});
		listMessages.refresh();
		listMessages.setItems(myReceiver.getMailbox(recipient));
		listMessages.scrollTo(listMessages.getItems().size() - 1);
		listMessages.refresh();
	}

	public void connect() {
		if (!(pseudo.getText().length() <= 3)) {
			try {
				myReceiver = new ReceiverImpl();
				try {
					myConnection = (Connection) Naming.lookup("rmi://" + Client.ipAdress + "/Connection");
				} catch (MalformedURLException | RemoteException | NotBoundException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("No server found at " + "rmi://" + Client.ipAdress + "/Connection");
					alert.setTitle("Server connection error");
					alert.show();
				}
				if (myConnection != null) {
					myEmitter = myConnection.connect(pseudo.getText(), myReceiver);
					if (myEmitter == null) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Connection error");
						alert.setContentText("Pseudo already connected in a session");
						alert.show();
					} else {
						myEmitter.setMyConnection(myConnection);
						listClients.setItems(myReceiver.getClients());
						for (String s : myReceiver.getClients()) {
							myReceiver.initMailbox(s);
						}
						initListView();
						labelConnected.setVisible(true);
						labelPseudo.setText(pseudo.getText());
						pseudo.setText("");
						animationConnect();
					}
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
		listMessages.scrollTo(listMessages.getItems().size() - 1);
		listMessages.refresh();
	}

	public void disconnect() throws RemoteException {
		if (myConnection != null) {
			myConnection.disconnect(labelPseudo.getText());
			bpMessages.setVisible(false);
			listMessages.getItems().clear();
			labelRecipient.setText("");
			animationDisconnect();
		} else {
			System.exit(0);
		}
	}

	public void close() throws RemoteException {
		if (myConnection != null) {
			myConnection.disconnect(labelPseudo.getText());
		}
		System.exit(0);
	}

	public void animationConnect() {
		animationConnect = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (hboxConnect.getOpacity() > 0) {
					hboxConnect.setOpacity(hboxConnect.getOpacity() - 0.02);
				} else {
					if (paneActions.getOpacity() < 1) {
						hboxLabel.setOpacity(hboxLabel.getOpacity() + 0.02);
						paneActions.setOpacity(paneActions.getOpacity() + 0.02);
					} else {
						animationConnect.stop();
					}
				}
			}
		}));
		animationConnect.setCycleCount(Timeline.INDEFINITE);
		animationConnect.play();
	}

	public void animationDisconnect() {
		animationDisconnect = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (paneActions.getOpacity() > 0) {
					hboxLabel.setOpacity(hboxLabel.getOpacity() - 0.02);
					paneActions.setOpacity(paneActions.getOpacity() - 0.02);
				} else {
					if (hboxConnect.getOpacity() < 1) {
						hboxConnect.setOpacity(hboxConnect.getOpacity() + 0.02);
					} else {
						labelPseudo.setText("");
						animationDisconnect.stop();
					}
				}
			}
		}));
		animationDisconnect.setCycleCount(Timeline.INDEFINITE);
		animationDisconnect.play();
	}

	public void changeIP() throws IOException {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		Parent root = FXMLLoader.load(Client.class.getResource("ChangeIP.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(Client.class.getResource("../css/styleAnalyse.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Change server IP adress");
		stage.getIcons().add(new Image(new FileInputStream(Client.class.getResource("../css/options.png").getPath())));
		stage.centerOnScreen();
		stage.show();
	}

}

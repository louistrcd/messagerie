package application;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

public class ControllerGUI implements Initializable{

	Dialogue myComponent;
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
	ListView listMessages;
	@FXML
	TextArea detailMessage;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			myComponent = (Dialogue) Naming.lookup("rmi://localhost:1099/Dialogue");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
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
	
	private void initListMessages() {
		try {
			List<String> messages = myComponent.getMessages(labelPseudo.getText());
			for(int i = messages.size()-1; i>=0 ; i--) {
				listMessages.getItems().add(messages.get(i));
			}
//			for(String message : messages) {
//				listMessages.getItems().add(message);
//			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		if(!(pseudo.getText().length()<=3)) {
			try {
				myComponent.connect(pseudo.getText());
				System.out.println("Your are connected as " + pseudo.getText());
				labelConnected.setVisible(true);
				labelPseudo.setText(pseudo.getText());
				pseudo.setText("");
				hboxConnect.setVisible(false);
				initListView();
				initListMessages();
				paneActions.setVisible(true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("You must enter a valid name");
			alert.setTitle("Pseudonym error");
			alert.show();
		}
	}
	
	public void newMessage() {
		try {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			FXMLLoader loader = new FXMLLoader(Client.class.getResource("NewMessage.fxml"));
			ControllerNewMessage controller = new ControllerNewMessage(labelPseudo.getText());
			loader.setController(controller);
			
			Scene scene = new Scene(loader.load(), 500 , 400);
			stage.setScene(scene);
			stage.setTitle("New message");			
			stage.show();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void disconnect() {
		try {
			myComponent.disconnect(labelPseudo.getText());
			hboxConnect.setVisible(true);
			labelConnected.setVisible(false);
			labelPseudo.setText("");
			paneActions.setVisible(false);
			listMessages.getItems().clear();
			detailMessage.setText("");
			detailMessage.setVisible(false);
		} catch (RemoteException e) {
		}
	}

}

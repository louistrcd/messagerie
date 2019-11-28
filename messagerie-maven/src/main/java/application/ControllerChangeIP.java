package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ControllerChangeIP implements Initializable{
	
	@FXML
	Label labelIP;
	@FXML
	TextField textField;
	@FXML
	Button button;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		labelIP.setText(Client.ipAdress);
		textField.setText(labelIP.getText());
		textField.selectEnd();
		button.requestFocus();
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					change();
				}
			}
		});
		textField.selectEnd();
	}
	
	@FXML
	public void change() {
		labelIP.setText(textField.getText());
		Client.ipAdress = textField.getText();
		textField.setText("");
	}
	
	

}

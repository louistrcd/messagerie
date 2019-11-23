package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReceiverImpl extends UnicastRemoteObject implements Receiver {

	ObservableList<String> connected = FXCollections.observableArrayList();
	HashMap<String, ObservableList<String>> mailbox = new HashMap<String, ObservableList<String>>();
	ControllerGUI controller;

	public ReceiverImpl() throws RemoteException {
		super();
	}

	public void setController(ControllerGUI controller, String pseudo) {
		this.controller = controller;
	}

	@Override
	public ObservableList<String> getClients() {
		return connected;
	}

	public ObservableList<String> getMailbox(String pseudo) {
		return mailbox.get(pseudo);
	}

	@Override
	public void receive(String from, String text){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (mailbox.get(from) == null) {
					ObservableList<String> messagesFrom = FXCollections.observableArrayList();
					messagesFrom.add(text);
					mailbox.put(from, messagesFrom);
				} else {
					mailbox.get(from).add(text);
				}
			}
		});
	}
	
	public void initMailbox(String pseudo) {
		ObservableList<String> test = FXCollections.observableArrayList();
		test.add("Bienvenue dans le chat");
		mailbox.put(pseudo, test);
	}

	@Override
	public void initClients(List<String> Clients) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				connected.clear();
				connected.addAll(Clients);
				for(String s : Clients) {
					initMailbox(s);
				}
			}
		});

	}

}

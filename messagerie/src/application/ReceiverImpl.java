package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReceiverImpl extends UnicastRemoteObject implements Receiver{
	
	ObservableList<String> connected = FXCollections.observableArrayList();
	ObservableList<String> messages = FXCollections.observableArrayList();
	HashMap<String, ObservableList<String>> mailbox = new HashMap<String, ObservableList<String>>();
	ControllerGUI controller;
	
	public ReceiverImpl() throws RemoteException {
		super();
	}
	
	public void setController(ControllerGUI controller) {
		this.controller = controller;
	}
	
	@Override
	public ObservableList<String> getMessages(){
		return messages;
	}
	
	@Override
	public ObservableList<String> getClients(){
		return connected;
	}
	
	public ObservableList<String> getMailbox(String pseudo){
		return mailbox.get(pseudo);
	}
	
	@Override
	public void receive(String from, String text) throws IllegalStateException{
		if(mailbox.get(from)==null) {
			ObservableList<String> messagesFrom = FXCollections.observableArrayList();
			messagesFrom.add(text);
			mailbox.put(from, messagesFrom);
		}else {
			mailbox.get(from).add(text);
		}
		Platform.runLater(new Runnable(){
			@Override
			public void run() {

			}
			});

	}

	@Override
	public void initClients(List<String> Clients) {
		connected.clear();
		connected.addAll(Clients);
	}

	@Override
	public void addClient(String client) {
		connected.add(client);
		
	}

	@Override
	public void remClient(String client) {
		connected.remove(client);
	}

}

package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.stage.Stage;

public class ConnectionImpl extends UnicastRemoteObject implements Connection {

	private static final long serialVersionUID = 1L;
	
	HashMap<String, Receiver> receivers = new HashMap<String, Receiver>();
	HashMap<String, Emitter> emitters = new HashMap<String, Emitter>();
	List<String> connected = new ArrayList<String>();

	protected ConnectionImpl() throws RemoteException {
		super();
	}
	
	@Override
	public List<String> getConnected(){
		return connected;
	}
	
	@Override
	public Receiver getReceiverOf(String pseudo) throws RemoteException {
		Receiver receiver = null;
		if(receivers.get(pseudo)!=null) {
			receiver = receivers.get(pseudo);
		}else {
			receiver = new ReceiverImpl(connected);
		}
		
		return receiver;	
	}

	@Override
	public Emitter connect(String pseudo, Receiver rcv) throws RemoteException {
		Emitter emitter = null;
		if(connected.contains(pseudo)) {
			emitter = null;
		}else {
			if(!receivers.containsValue(pseudo)) {
				emitter = new EmitterImpl(pseudo);
				receivers.put(pseudo, rcv);
				System.out.println("Receivers size " + receivers.size());
			}
			connected.add(pseudo);
		}
		return emitter;
	}

	@Override
	public void disconnect(String pseudo) {
		connected.remove(pseudo);
	}
	
}

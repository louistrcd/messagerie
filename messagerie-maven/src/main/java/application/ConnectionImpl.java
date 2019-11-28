package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Receiver getReceiverOf(String pseudo) {
		return receivers.get(pseudo);
	}

	@Override
	public Emitter connect(String pseudo, Receiver rcv) throws RemoteException {
		Emitter emitter = null;
		if(connected.contains(pseudo)) {
			emitter = null;
		}else {
			connected.add(pseudo);
			if(!receivers.containsValue(pseudo)) {
				emitter = new EmitterImpl(pseudo);
				receivers.put(pseudo, rcv);
				for(Map.Entry<String, Receiver> tupple : receivers.entrySet()) {
					tupple.getValue().initClients(connected);
				}
			}
		}
		return emitter;
	}

	@Override
	public void disconnect(String pseudo) throws RemoteException {
		connected.remove(pseudo);
		receivers.remove(pseudo);
		for(Map.Entry<String, Receiver> tupple : receivers.entrySet()) {
			tupple.getValue().initClients(connected);
		}
	}
	
}

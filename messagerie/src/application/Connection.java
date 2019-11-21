package application;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Connection extends Remote{
	Emitter connect(String pseudo, Receiver rcv);
	void disconnect(String pseudo);
}

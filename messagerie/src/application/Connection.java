package application;

import java.rmi.Remote;

public interface Connection extends Remote{
	
	Dialogue connect(String pseudo);
	void disconnect(String pseudo);

}

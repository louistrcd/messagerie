package application;

public interface Connection {
	
	Dialogue connect(String pseudo);
	void disconnect(String pseudo);

}

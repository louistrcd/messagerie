
public class Message {
	
	private String from;
	private String text;
	
	public Message(String from, String text) {
		this.from = from;
		this.text = text;
		
	}
	
	public String showMessage(){
		String message = "Exp�diteur : " + from + "\n" + text;
		System.out.println(message);
		return message;
	}
	
	

}

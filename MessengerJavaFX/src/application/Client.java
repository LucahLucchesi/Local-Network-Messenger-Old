package application;
import java.net.*;
import java.util.Base64;


import javafx.scene.control.TextArea;


import java.io.*;

public class Client {
	

	private Socket socket = null;
	private TextArea chatBox;
	private boolean isClosing = false;
	
	PrintWriter output = null;
	
	
	
	
	public Client(String serverIp, int serverPort, TextArea chatBox) throws UnknownHostException, IOException {
		this.chatBox = chatBox;
		socket = new Socket(serverIp, serverPort);
	}
	
	public void run() throws IOException {
		
		

		ConnectionHandler serverConnection = new ConnectionHandler(socket, chatBox);
		
		output = new PrintWriter(socket.getOutputStream(), true);

		
		new Thread(serverConnection).start();
		
		if(isClosing) {
			System.out.println("program closed");
			output.println(Base64.getEncoder().encodeToString("[System]: Client Disconnected.".getBytes("UTF-8")));
			socket.close();
			System.exit(0);
		}
		
		
		
		
	}
	
	public void sendClientMessage(String msg) {
		try {
			String encodedMsg = Base64.getEncoder().encodeToString(msg.getBytes("UTF-8"));
			output.println(encodedMsg);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("problem encoding");
		}
	}
	
	public void closeConnection() {
		isClosing = true;
	}

}

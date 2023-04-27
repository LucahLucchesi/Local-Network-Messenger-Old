package application;
import java.net.*;
import java.util.Base64;


import javafx.scene.control.TextArea;


import java.io.*;

public class Server {
	
	private ServerSocket server = null;
	private TextArea chatBox;
	PrintWriter output = null;
	
	public Server(int serverPort, TextArea chatBox) throws IOException {
		this.chatBox = chatBox;
		server = new ServerSocket(serverPort);
	}
	
	public void run() throws IOException {
		System.out.println("[System]: Waiting for client...");
		Socket client = server.accept();
		System.out.println("[System]: Client connected.");
		
		
		ConnectionHandler serverConnection = new ConnectionHandler(client, chatBox);
		output = new PrintWriter(client.getOutputStream(), true);
		
		new Thread(serverConnection).start();
		
		if(client.isConnected() == false) {
			client.close();
			server.close();
			System.exit(0);
		}
		
	}
	
	public void sendServerMessage(String msg) {
		try {
			String encodedMsg = Base64.getEncoder().encodeToString(msg.getBytes("UTF-8"));
			output.println(encodedMsg);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("problem encoding");
		}
	}

}

package application;
import java.net.*;
import java.util.Base64;


import javafx.scene.control.TextArea;


import java.io.*;

public class Server implements Runnable{
	
	private ServerSocket server = null;
	private TextArea chatBox;
	PrintWriter output = null;
	private boolean isClosing = false;
	
	public Server(int serverPort, TextArea chatBox) throws IOException {
		this.chatBox = chatBox;
		server = new ServerSocket(serverPort);
	}
	@Override
	public void run() {
		chatBox.appendText("[System]: Waiting for client...\n");
		Socket client = null;
		try {
			client = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chatBox.appendText("[System]: Client connected.\n");
		
		
		ConnectionHandler serverConnection = null;
		try {
			serverConnection = new ConnectionHandler(client, chatBox);
			output = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Thread(serverConnection).start();
		
	
		if(isClosing) {
			try {
				client.close();
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public void closeConnection() {
		isClosing = true;
	}

}

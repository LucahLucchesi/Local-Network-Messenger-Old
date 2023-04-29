package application;
import java.net.*;
import java.util.Base64;


import javafx.scene.control.TextArea;


import java.io.*;

public class Client implements Runnable{
	

	private Socket socket = null;
	private TextArea chatBox;
	private boolean isClosing = false;
	
	PrintWriter output = null;
	
	public Client(String serverIp, int serverPort, TextArea chatBox) throws UnknownHostException, IOException {
		this.chatBox = chatBox;
		socket = new Socket(serverIp, serverPort);
	}
	@Override
	public void run() {
		ConnectionHandler serverConnection = null;
		try {
			serverConnection = new ConnectionHandler(socket, chatBox);
			output = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		
		new Thread(serverConnection).start();

		if(isClosing) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
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

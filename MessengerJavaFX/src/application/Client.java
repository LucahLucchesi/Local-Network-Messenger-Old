package application;
import java.net.*;
import java.util.Base64;


import javafx.scene.control.TextArea;


import java.io.*;

/**
 * @author Alex Berg
 * @section CS-145-001
 * 
 * This handles the client's connection to the server as well as sending packets.
 */
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
		// creates a new instance of connectionHandler
		// Creates a PrintWriter set to output messages to the server
		ConnectionHandler serverConnection = null;
		try {
			serverConnection = new ConnectionHandler(socket, chatBox);
			output = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		new Thread(serverConnection).start();
		
		// if true, close the connection.
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
	
	/**
	 * @param msg 
	 * 
	 * Encodes message into base64 then sends the message to the server.
	 */
	public void sendClientMessage(String msg) {
		try {
			String encodedMsg = Base64.getEncoder().encodeToString(msg.getBytes("UTF-8"));
			output.println(encodedMsg);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Sets boolean isClosing to true in order to close the connection.
	 */
	public void closeConnection() {
		isClosing = true;
	}

}

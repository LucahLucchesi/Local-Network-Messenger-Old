package application;
import java.net.*;
import java.util.Base64;


import javafx.scene.control.TextArea;


import java.io.*;

/**
 * @author Alex Berg
 * @section CS-145-001
 * 
 * This handles the server's connection the the client as well as sending packets.
 */
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
		// lets user know that server is open and waiting for someone to join
		chatBox.appendText("[System]: Waiting for client...\n");
		Socket client = null;
		ConnectionHandler serverConnection = null;
		
		// waits until client connects. once a client connects, create connectionHandler and output stream.
		try {
			client = server.accept();
			serverConnection = new ConnectionHandler(client, chatBox);
			output = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			// If there is an error, close the program.
			System.out.println(e.getMessage());
			isClosing = true;
			System.exit(0);
		}
		// let user know that a client has connected.
		chatBox.appendText("[System]: Client connected.\n");
		
		new Thread(serverConnection).start();
		
		// if true, close the server and connection.
		if(isClosing) {
			try {
				client.close();
				server.close();
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
	 * Encodes message into base64 then sends the message to the client.
	 */
	public void sendServerMessage(String msg) {
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

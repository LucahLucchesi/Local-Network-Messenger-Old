package application;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

import javafx.scene.control.TextArea;

/**
 * @author Alex Berg
 * @section CS-145-001
 *
 * Both server and client starts a new thread of ConnectionHandler to manage incoming packets.
 */
public class ConnectionHandler implements Runnable {
	private Socket server;
	private BufferedReader in;
	@SuppressWarnings("unused")
	private TextArea chatBox;
	
	public ConnectionHandler(Socket server, TextArea chatBox) throws IOException {
		this.setServer(server);
		this.chatBox = chatBox;
		in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		
	}

	@Override
	public void run() {
		
		try {
			// Constantly looks for incoming message.
			// If a message is received, decode it and display it.
			while(true) {
				String response = null;
				response = in.readLine();
				
				// Breaks while loop if there is no response from server/client.
				// If connection is valid, response will not be null.
				if(response == null) break;
				byte[] decodedMsg = Base64.getDecoder().decode(response);
				String msg = new String(decodedMsg, "UTF-8");
				printMsg(msg);
			}
		}catch(IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
		
	}

	public Socket getServer() {
		return server;
	}

	public void setServer(Socket server) {
		this.server = server;
	}
	
	/**
	 * @param msg
	 * 
	 * Adds message to chatBox and makes a new line
	 */
	public void printMsg(String msg) {
		chatBox.appendText(msg + "\n");
	}

}

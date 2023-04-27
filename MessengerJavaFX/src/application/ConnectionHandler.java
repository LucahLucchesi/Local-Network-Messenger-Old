package application;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

import javafx.scene.control.TextArea;

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
			while(true) {
				String serverResponse = null;
				serverResponse = in.readLine();
				if(serverResponse == null) break;
				byte[] decodedMsg = Base64.getDecoder().decode(serverResponse);
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
	
	public void printMsg(String msg) {
		chatBox.appendText(msg + "\n");
	}

}

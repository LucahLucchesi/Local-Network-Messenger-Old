package application;
import java.net.*;
import java.util.Base64;
import java.util.Scanner;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;

public class Client {
	
	private String userName;
	private Socket socket = null;
	private TextArea chatBox;
	private TextField msgField;
	
	
	public Client(String userName, String serverIp, int serverPort, TextArea chatBox, TextField msgField) throws UnknownHostException, IOException {
		this.userName = userName;
		this.chatBox = chatBox;
		this.msgField = msgField;
		socket = new Socket(serverIp, serverPort);
	}
	
	public void run() throws IOException {
		Scanner s = new Scanner(System.in);

		
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		

		ConnectionHandler serverConnection = new ConnectionHandler(socket, chatBox);
		
		PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

		
		String msg = "";
		
		new Thread(serverConnection).start();
		
		while(!msg.equals("END")) {
			System.out.println("message: ");
			msg = keyboard.readLine();
			
			String msgFull = "[" + userName + "]: " + msg;
			String encodedMsg = Base64.getEncoder().encodeToString(msgFull.getBytes("UTF-8"));
			
			output.println(encodedMsg);
		}
		
		s.close();
		socket.close();
		System.exit(0);
		
		
	}
	
	public String sendMessage() {
		String msg = msgField.getText();
		msgField.clear();
		return msg;
	}

}

package application;
import java.net.*;
import java.util.Base64;
import java.util.Scanner;
import java.io.*;

public class Server {
	
	private String serverName;
	private ServerSocket server = null;
	
	
	public Server(String serverName, int serverPort) throws IOException {
		this.serverName = serverName;
		server = new ServerSocket(serverPort);
	}
	
	public void run() throws IOException {
		Scanner s = new Scanner(System.in);
		System.out.println("[System]: Waiting for client...");
		Socket client = server.accept();
		System.out.println("[System]: Client connected.");
		

		
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		
		ConnectionHandler serverConnection = new ConnectionHandler(client);
		PrintWriter output = new PrintWriter(client.getOutputStream(), true);
		

		String msg = "";
		
		new Thread(serverConnection).start();
		
		while(!msg.equals("END")) {
			System.out.println("message: ");
			msg = keyboard.readLine();
			String fullMsg = "[" + serverName + "]: " + msg;
			String encodedMsg = Base64.getEncoder().encodeToString(fullMsg.getBytes("UTF-8"));
			output.println(encodedMsg);
			
		}
		
		s.close();
		client.close();
		server.close();
		System.exit(0);
	}

}

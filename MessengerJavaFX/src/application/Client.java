package application;
import java.net.*;
import java.util.Base64;
import java.util.Scanner;
import java.io.*;

public class Client {
	
	private String userName;
	private Socket socket = null;
	
	
	public Client(String userName, String serverIp, int serverPort) throws UnknownHostException, IOException {
		this.userName = userName;
		socket = new Socket(serverIp, serverPort);
	}
	
	public void run() throws IOException {
		Scanner s = new Scanner(System.in);

		
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		

		ConnectionHandler serverConnection = new ConnectionHandler(socket);
		
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

}

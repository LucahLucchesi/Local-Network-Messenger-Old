package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
	String username;
	int port;
	String ip;
	Server hostServer = null;
	Client client = null;
	
	@Override
	public void start(Stage app) {
		try {
			
			
			//Info Window Setup
			RadioButton hostButton = new RadioButton("Host");
			RadioButton joinButton = new RadioButton("Join");
			ToggleGroup group = new ToggleGroup();
			Label userLabel = new Label("Username:");
			Label ipLabel = new Label("Host IP:");
			Label portLabel = new Label("Port:");
			TextField userField = new TextField();
			TextField ipField = new TextField();
			TextField portField = new TextField();
			Button confirm = new Button("Confirm");
			
			hostButton.setToggleGroup(group);
			joinButton.setToggleGroup(group);
			hostButton.setPadding(new Insets(10));
			joinButton.setPadding(new Insets(10));
			
			userLabel.setPadding(new Insets(0, 10, 0, 10));
			portLabel.setPadding(new Insets(0, 41, 0, 10));
			ipLabel.setPadding(new Insets(0, 25, 0, 10));
			
			userField.setEditable(false);
			userField.setPrefColumnCount(8);
			portField.setEditable(false);
			portField.setPrefColumnCount(8);
			ipField.setEditable(false);
			ipField.setPrefColumnCount(8);
			
			
			confirm.setDisable(true);
			
			HBox radioButtons = new HBox(hostButton, joinButton);
			HBox usernameBox = new HBox(userLabel, userField);
			HBox ipBox = new HBox(ipLabel, ipField);
			HBox portBox = new HBox(portLabel, portField);
			
			VBox infoWindow = new VBox(radioButtons, usernameBox, portBox, ipBox, confirm);
			
			usernameBox.setPadding(new Insets(0, 0, 5, 0));
			ipBox.setPadding(new Insets(0, 0, 5, 0));
			portBox.setPadding(new Insets(0, 0, 5, 0));
			infoWindow.setPadding(new Insets(0, 10, 10, 10));
			
			Scene infoScene = new Scene(infoWindow, 220, 170);
			
			//Chat window setup
			TextArea chatBox = new TextArea();
			TextField messageField = new TextField();
			Button sendButton = new Button("Send");
			
			chatBox.setEditable(false);
			chatBox.setMaxSize(400, 250);
			chatBox.setWrapText(true);
			messageField.setEditable(true);
			messageField.setPrefColumnCount(20);
			sendButton.setDefaultButton(true);
			
			HBox messageSender = new HBox(messageField, sendButton);
			messageSender.setPadding(new Insets(10, 0, 0, 0));
			VBox chatWindow = new VBox(chatBox, messageSender);
			chatWindow.setPadding(new Insets(10, 10, 10, 10));
			
			Scene chatBoxScene = new Scene(chatWindow);
			
			app.setScene(infoScene);
			app.setTitle("Info");
			app.show();
			
			app.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					if(hostServer != null) {
						hostServer.sendServerMessage("[System]: Server Closed.");
						hostServer.closeConnection();
					}else if(client != null) {
						client.sendClientMessage("[System]: Client Disconnected.");
						client.closeConnection();
					}
					Platform.exit();
					System.exit(0);
				}
				
			});
			
			hostButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					userField.setEditable(true);
					portField.setEditable(true);
					ipField.setEditable(false);
					ipField.clear();
					confirm.setDisable(false);
				}
			});
			joinButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					userField.setEditable(true);
					portField.setEditable(true);
					ipField.setEditable(true);
					confirm.setDisable(false);
				}
			});
			confirm.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					if(hostButton.isSelected()) {
						username = userField.getText();
						port = Integer.parseInt(portField.getText());
						try {
							app.close();
							app.setScene(chatBoxScene);
							app.setTitle("MessengerFX");
							app.show();
							hostServer = new Server(port, chatBox);
							new Thread(hostServer).start();
						}catch(IOException e) {
							System.out.println("Server creation failed");
						}
					}else {
						username = userField.getText();
						port = Integer.parseInt(portField.getText());
						ip = ipField.getText();
						try {
							app.close();
							app.setScene(chatBoxScene);
							app.setTitle("MessengerFX");
							app.show();
							client = new Client(ip, port, chatBox);
							new Thread(client).start();
						}catch(IOException e) {
							System.out.println("Connection failed");
						}
						
					}
				}
			});
			sendButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String fullMsg = "[" + username + "]: " + messageField.getText();
					chatBox.appendText(fullMsg + "\n");
					messageField.clear();
					if(hostServer != null) {
						hostServer.sendServerMessage(fullMsg);
					}else {
						client.sendClientMessage(fullMsg);
					}
				}
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

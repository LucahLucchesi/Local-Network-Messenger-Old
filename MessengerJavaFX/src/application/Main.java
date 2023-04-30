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

/**
 * @author Lucah L, Alex B.
 * @section CS145-001
 * 
 * Main creates the Info Box and Chat Box GUIs, as well as handling all events.
 */
public class Main extends Application {
	String username;
	int port;
	String ip;
	Server hostServer = null;
	Client client = null;
	
	/**
	 *GUI creation and event handlers
	 */
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
			//Component settings
			hostButton.setToggleGroup(group);
			joinButton.setToggleGroup(group);
			userField.setEditable(false);
			portField.setEditable(false);
			ipField.setEditable(false);
			userField.setPrefColumnCount(8);
			portField.setPrefColumnCount(8);
			ipField.setPrefColumnCount(8);
			confirm.setDisable(true);
			//Component padding
			hostButton.setPadding(new Insets(10));
			joinButton.setPadding(new Insets(10));
			userLabel.setPadding(new Insets(0, 10, 0, 10));
			portLabel.setPadding(new Insets(0, 41, 0, 10));
			ipLabel.setPadding(new Insets(0, 25, 0, 10));
			//Component groups
			HBox radioButtons = new HBox(hostButton, joinButton);
			HBox usernameBox = new HBox(userLabel, userField);
			HBox ipBox = new HBox(ipLabel, ipField);
			HBox portBox = new HBox(portLabel, portField);
			VBox infoWindow = new VBox(radioButtons, usernameBox, portBox, ipBox, confirm);
			//Component group padding
			usernameBox.setPadding(new Insets(0, 0, 5, 0));
			ipBox.setPadding(new Insets(0, 0, 5, 0));
			portBox.setPadding(new Insets(0, 0, 5, 0));
			infoWindow.setPadding(new Insets(0, 10, 10, 10));
			//Info window scene
			Scene infoScene = new Scene(infoWindow, 220, 170);
			//Chat window setup
			TextArea chatBox = new TextArea();
			TextField messageField = new TextField();
			Button sendButton = new Button("Send");
			//Component settings
			chatBox.setEditable(false);
			chatBox.setMaxSize(400, 250);
			chatBox.setWrapText(true);
			messageField.setEditable(true);
			messageField.setPrefColumnCount(20);
			sendButton.setDefaultButton(true);
			//Component groups
			HBox messageSender = new HBox(messageField, sendButton);
			VBox chatWindow = new VBox(chatBox, messageSender);
			//Component group padding
			messageSender.setPadding(new Insets(10, 0, 0, 0));
			chatWindow.setPadding(new Insets(10, 10, 10, 10));
			//Chat window scene
			Scene chatBoxScene = new Scene(chatWindow);
			//Stage is initialized with the info window scene
			app.setScene(infoScene);
			app.setTitle("Info");
			app.show();
			//Safely closes any open server upon closing the GUI window
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
			//When the host radio button is activated, the Username and Port fields enabled for input. The IP field is disabled
			//because the host does not need to enter their own IP address
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
			//When the join radio button is activated, all info fields are enabled for input
			joinButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					userField.setEditable(true);
					portField.setEditable(true);
					ipField.setEditable(true);
					confirm.setDisable(false);
				}
			});
			//When the confirm button is pressed, the global variables for username, port, and ip are assingned with the text
			//in the info fields. Then the scene swaps to the chat box and a new connection is made.
			confirm.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					username = userField.getText();
					port = Integer.parseInt(portField.getText());
					if(hostButton.isSelected()) {
						try {
							app.close();
							app.setScene(chatBoxScene);
							app.setTitle("MessengerFX");
							app.show();
							//new server object is made
							hostServer = new Server(port, chatBox);
							new Thread(hostServer).start();
						}catch(IOException e) {
							System.out.println("Server creation failed");
						}
					}else {
						ip = ipField.getText();
						try {
							app.close();
							app.setScene(chatBoxScene);
							app.setTitle("MessengerFX");
							app.show();
							//new client object is made
							client = new Client(ip, port, chatBox);
							new Thread(client).start();
						}catch(IOException e) {
							System.out.println("Connection failed");
						}
					}
				}
			});
			//Upon pressing the send button, any text in the message field will be appended to the chat box, and then
			//the message is sent to the host/client
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

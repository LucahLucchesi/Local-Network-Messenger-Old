package application;
	
import javafx.application.Application;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
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
			Insets inset = new Insets(10);
			
			hostButton.setToggleGroup(group);
			joinButton.setToggleGroup(group);
			hostButton.setPadding(inset);
			joinButton.setPadding(inset);
			
			userLabel.setPadding(inset);
			ipLabel.setPadding(inset);
			portLabel.setPadding(inset);
			
			userField.setEditable(false);
			userField.setPrefColumnCount(15);
			userField.setPadding(inset);
			ipField.setEditable(false);
			ipField.setPrefColumnCount(15);
			ipField.setPadding(inset);
			portField.setEditable(false);
			portField.setPrefColumnCount(5);
			portField.setPadding(inset);
			
			confirm.setDisable(true);
			
			HBox radioButtons = new HBox(hostButton, joinButton);
			HBox username = new HBox(userLabel, userField);
			HBox ip = new HBox(ipLabel, ipField);
			HBox port = new HBox(portLabel, portField);
			
			VBox infoWindow = new VBox(radioButtons, username, port, ip, confirm);
			Scene infoScene = new Scene(infoWindow, 300, 300);
			
			//Chat window setup
			TextArea chatBox = new TextArea();
			TextField messageField = new TextField();
			Button sendButton = new Button("send");
			
			chatBox.setEditable(false);
			chatBox.setMaxSize(400, 250);
			messageField.setEditable(true);
			
			HBox messageSender = new HBox(messageField, sendButton);
			VBox chatWindow = new VBox(chatBox, messageSender);
			chatWindow.setPadding(new Insets(10, 10, 10, 10));
			
			Scene chatBoxScene = new Scene(chatWindow);
			
			app.setScene(infoScene);
			app.setTitle("Info");
			app.show();
			
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
					app.close();
					app.setScene(chatBoxScene);
					app.setTitle("MessengerFX");
					app.show();
					
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

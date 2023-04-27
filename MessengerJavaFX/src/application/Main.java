package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
			RadioButton hostButton = new RadioButton("Host");
			RadioButton joinButton = new RadioButton("Join");
			ToggleGroup group = new ToggleGroup();
			Label userLabel = new Label("Username:");
			Label ipLabel = new Label("Host IP:");
			Label portLabel = new Label("Port:");
			TextField userField = new TextField();
			TextField ipField = new TextField();
			TextField portField = new TextField();
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
			
			HBox radioButtons = new HBox(hostButton, joinButton);
			HBox username = new HBox(userLabel, userField);
			HBox ip = new HBox(ipLabel, ipField);
			HBox port = new HBox(portLabel, portField);
			
			Button confirm = new Button("Confirm");
			
			VBox infoWindow = new VBox(radioButtons, username, ip, port, confirm);
			Scene infoScene = new Scene(infoWindow, 300, 300);
			Scene chatBoxScene = new Scene(new Pane());
			app.setScene(infoScene);
			app.setTitle("Info");
			app.show();
			
			confirm.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					app.close();
					app.setScene(chatBoxScene);
					app.show();
					
				}
			});
			
//			TextArea chatBox = new TextArea();
//			chatBox.setEditable(false);
//			chatBox.setMaxSize(400, 250);
//			
//			//this is how we add text to the chatbox. a new line must follow every message
//			chatBox.appendText("Hello\n");
//			chatBox.appendText("Hi\n");
//			
//			TextField messageField = new TextField();
//			messageField.setEditable(true);
//			
//			Button sendButton = new Button("send");
//			HBox test = new HBox(messageField, sendButton);
//			VBox test2 = new VBox(chatBox, test);
//			test2.setPadding(new Insets(10, 10, 10, 10));
//			
//			Scene scene = new Scene(test2, 400, 300);
//			
//			
//			app.setScene(scene);
//			app.setTitle("Private Messenger");
//			app.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

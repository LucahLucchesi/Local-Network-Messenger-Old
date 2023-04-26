package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage app) {
		try {
			
			
			TextArea chatBox = new TextArea();
			chatBox.setEditable(false);
			chatBox.setMaxSize(400, 250);
			
			//this is how we add text to the chatbox. a new line must follow every message
			chatBox.appendText("Hello\n");
			chatBox.appendText("Hi\n");
			for(int i = 0; i < 30; i++) {
				chatBox.appendText("Hi\n");
			}
			
			TextField messageField = new TextField();
			messageField.setEditable(true);
			
			Button sendButton = new Button("send");
			HBox test = new HBox(messageField, sendButton);
			VBox test2 = new VBox(chatBox, test);
			test2.setPadding(new Insets(10, 10, 10, 10));
			
//			GridPane pane = new GridPane();
//			pane.setPadding(new Insets(10, 10, 10, 10));
//			pane.add(chatBox, 0, 0);
//			pane.add(messageField, 0, 1);
//			pane.add(sendButton, 1, 1);
			Scene scene = new Scene(test2, 400, 300);
			
			
			app.setScene(scene);
			app.setTitle("Private Messenger");
			app.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

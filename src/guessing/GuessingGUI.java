package guessing;

import java.io.IOException; 
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Stack;

import guessing.Prompt;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import networking.*;

public class GuessingGUI extends Application {
	private int portNumber = 2000;
	private String ServerAddress;
	private String word;

	public static void main(String[] args) {
		launch();
	}

	public void start(Stage primaryStage) throws Exception {
		//client 
		Client client = new Client();
		client.setServerAddr(ServerAddress);
		client.setPort(portNumber);
		client.setName("Client1");
		
		// objects
		Pane root = new Pane(); // base for all layouts
		StackPane pane = new StackPane(); // holds the canvas
		Canvas canvas = new Canvas(350, 350); // canvas
		Prompt prompt = new Prompt();
		LetterBank bank = new LetterBank();

		// set up input text box
		TextField guessInput = new TextField();
		guessInput.setLayoutX(50);
		guessInput.setLayoutY(427);
		guessInput.setPrefWidth(170);

		// undo button
		Button undo = new Button();
		undo.setMinHeight(33);
		undo.setMinWidth(70);
		undo.setStyle(
				"-fx-background-color: SteelBlue; -fx-text-fill: #ffffff; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 14px;  -fx-font-weight: bold;");
		undo.setText("UNDO");
		undo.setLayoutX(350);
		undo.setLayoutY(462);

		Stack undoStack = new Stack(); // create stack object that records all the letters

		// submit button
		Button submit = new Button();
		submit.setMinHeight(33);
		submit.setMinWidth(70);
		submit.setStyle(
				"-fx-background-color: SteelBlue; -fx-text-fill: #ffffff; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 14px;  -fx-font-weight: bold;");
		submit.setText("CHECK!");
		submit.setLayoutX(350);
		submit.setLayoutY(503);

		// set up result (shows up when the user hits submit on the guess)
		Text result = new Text();
		result.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
		result.setFill(Color.SKYBLUE);

		// set up button that sends client information to server
		Button connectBtn = new Button("Connect");
		connectBtn.setLayoutX(290);

		// set up textfield to input host name
		TextField hostNameInput = new TextField();

		// set up error message (appears if hostName is not valid)
		Label errorLabel = new Label();
		errorLabel.setLayoutY(25);
		errorLabel.setTextFill(Color.RED);

		// sends client information over to the server
		connectBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent Event) {

			}
		});

		// undo button; undos the last letter
		undo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				undoStack.pop();
				undoStack.toArray(); // set to array
				guessInput.clear(); // clear previous text

				for (int i = 0; i < undoStack.size(); i++) { // print stack
					guessInput.appendText(undoStack.get(i).toString());
				}
			}

		});

		// submit button; checks if correct
		submit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (guessInput.getText().toLowerCase().equals(word)) {
					
					result.setText("Correct :)");
				}

				else {
					result.setText("Wrong :(");
				}
			}

		});
		
		connectBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ServerAddress = hostNameInput.getText();
				client.setServerAddr(ServerAddress);
				client.setPort(2000);
				client.connect();
				while (word == null) {
					word = client.read();
					System.out.println("WORD: " + word);
					
					// record the word they are trying to guess
					char[] charWordArray = bank.CharArray(word); // create char array

					// prompt that tells user the number of letters in the word
					Text letterNum = new Text("# of Letters: " + word.length());
					letterNum.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
					letterNum.setFill(Color.WHITE);
					letterNum.setLayoutX(270);
					letterNum.setLayoutY(446);
					
					// set up letter bank
					int n = 16;
					int row1 = 25;
					int row2 = 25;
					ArrayList<Character> letterBank = bank.getLetterBank(charWordArray);

					Button[] letterBox = new Button[n];

					for (int i = 0; i < 16; i++) {
						letterBox[i] = new Button(); // create new rectangles
						letterBox[i].setMinHeight(33);
						letterBox[i].setMinWidth(33);
						letterBox[i].setStyle(
								"-fx-background-color: LightSeaGreen; -fx-text-fill: #ffffff; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 14px;  -fx-font-weight: bold;");
						letterBox[i].setText(letterBank.get(i).toString());

						if (i < 8) { // first row
							letterBox[i].setLayoutX(row1);
							row1 = row1 + 41;
							letterBox[i].setLayoutY(462);
						}

						if (i >= 8) { // second row
							letterBox[i].setLayoutX(row2);
							row2 = row2 + 41;

							letterBox[i].setLayoutY(503);
						}

					}
					
					root.getChildren().add(letterNum);
					
					for (int i = 0; i < letterBox.length; i++)
						root.getChildren().addAll(letterBox[i]);
					
					// add text to text field once button is clicked
					for (int i = 0; i < letterBox.length; i++) {
						String text = letterBox[i].getText();
						letterBox[i].setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								guessInput.appendText(text);
								undoStack.add(text); // add text to stack
							}
						});
					}
					
					System.out.println(word);
				}
			}

		});
		// GraphicsContext
		GraphicsContext gc;
		gc = canvas.getGraphicsContext2D();

		// configuring the canvas
		pane.setStyle("-fx-background-color: white"); // sets canvas colour
		pane.setLayoutX(50); // find a way to center horizontally without doing it manually!!
		pane.setLayoutY(70);

		// set up scene
		Scene scene = new Scene(root, 450, 550);
		root.setBackground(new Background(new BackgroundFill(Color.POWDERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

		// adding all GUI elements
		pane.getChildren().addAll(canvas, result);
		root.getChildren().addAll(pane, guessInput, submit, undo, hostNameInput, errorLabel, connectBtn);

		

		// creates and shows the window
		primaryStage.setTitle("Guessing Component");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}

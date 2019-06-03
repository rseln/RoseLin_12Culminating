package guessComp;

import java.util.ArrayList;

import general.Prompt;
import javafx.application.Application;  
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.canvas.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GuessFace extends Application {

	public void start(Stage primaryStage) throws Exception {

		// objects
		Pane root = new Pane(); // base for all layouts
		StackPane pane = new StackPane(); // holds the canvas
		Canvas canvas = new Canvas(350, 350); // canvas
		Prompt prompt = new Prompt();
		
		//set up input text box
		TextField guessInput = new TextField();
		guessInput.setLayoutX(50);
		guessInput.setLayoutY(435);
		guessInput.setPrefWidth(170);
		
		ArrayList<String> wordList = new ArrayList<String>();
		wordList = prompt.getWordList();
		String word = prompt.getWord(wordList);
		System.out.println(word);

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
		pane.getChildren().addAll(canvas);
		root.getChildren().addAll(pane, guessInput);
		
		// creates and shows the window
		primaryStage.setTitle("Guessing Component");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String args[]) {
		launch(args);
	}

}

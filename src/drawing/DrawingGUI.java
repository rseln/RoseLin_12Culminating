package drawing;

import javafx.application.Application; 
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.SnapshotParameters;
import javafx.stage.Stage;
import networking.*;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import guessing.Prompt;

public class DrawingGUI extends Application implements ConnectionListener {

	private int portNumber = 2000;

	public static void main(String args[]) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		//server
		Server server = new Server("localhost", portNumber);
		server.addConnectionListener(this);
	
		// objects
		Pane root = new Pane(); // base for all layouts
		StackPane pane = new StackPane(); // holds the canvas
		Canvas canvas = new Canvas(350, 350); // canvas
		DrawingTools tool = new DrawingTools();
		Prompt prompt = new Prompt();

		// GraphicsContext
		GraphicsContext gc;
		gc = canvas.getGraphicsContext2D();

		// configuring the canvas
		pane.setStyle("-fx-background-color: white"); // sets canvas colour
		pane.setLayoutX(50);
		pane.setLayoutY(70);

		// get drawing prompt
		ArrayList<String> wordList = new ArrayList<String>();
		wordList = prompt.getWordList();
		String word = prompt.getWord(wordList);

		// set up and display word prompt
		Rectangle promptBase = new Rectangle(250, 55);
		promptBase.setFill(Color.DARKTURQUOISE);
		promptBase.setLayoutX(30);
		promptBase.setLayoutY(45);

		Text wordPrompt = new Text(word);
		wordPrompt.setFont(Font.font("Comic Sans MS", 36));
		wordPrompt.setFill(Color.WHITE);
		wordPrompt.setLayoutX(55);
		wordPrompt.setLayoutY(85);

		// set up drawing submission button
		Button submit = new Button("Submit");
		submit.setLayoutX(350);
		submit.setLayoutY(500);

		// set up screenshot
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);

		// set up error message
		Label errorLabel = new Label();
		errorLabel.setLayoutY(25);
		errorLabel.setTextFill(Color.RED);

		// set up eraser
		Image eraserImage = new Image("file:src/resources/eraser.png"); // loads image
		ImageView eraser = new ImageView();
		eraser.setImage(eraserImage);
		eraser.setFitWidth(50);
		eraser.setPreserveRatio(true);
		eraser.setLayoutX(340);
		eraser.setLayoutY(435);

		// set up brush
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);

		// set up colour selector
		Rectangle blue = new Rectangle(30, 30);
		blue.setFill(Color.BLUE);
		blue.setLayoutX(55);
		blue.setLayoutY(435);

		Rectangle red = new Rectangle(30, 30);
		red.setFill(Color.RED);
		red.setLayoutX(95);
		red.setLayoutY(435);

		Rectangle yellow = new Rectangle(30, 30);
		yellow.setFill(Color.YELLOW);
		yellow.setLayoutX(135);
		yellow.setLayoutY(435);

		Rectangle black = new Rectangle(30, 30);
		black.setFill(Color.BLACK);
		black.setLayoutX(175);
		black.setLayoutY(435);

		// EVENT HANDLERS //

		// eraser
		eraser.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				tool.eraser(gc);
			}
		});

		// colour selector
		blue.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				tool.brush("blue", gc);
			}
		});

		red.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				tool.brush("red", gc);
			}
		});

		yellow.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				tool.brush("yellow", gc);
			}
		});

		black.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				tool.brush("black", gc);

			}
		});

		// starts a new line path once the mouse is pressed
		canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				gc.beginPath();
			}
		});

		// draws the lines
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				gc.lineTo(event.getX(), event.getY());
				gc.stroke();
			}
		});

		//sends information over to the connected client 
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent Event) {
				server.WaitForConnections();
				WritableImage image = canvas.snapshot(params, null); // take snapshot of canvas
			}
		});


		// set up scene
		Scene scene = new Scene(root, 450, 550);
		root.setBackground(new Background(new BackgroundFill(Color.POWDERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

		// adding all GUI elements
		pane.getChildren().addAll(canvas);
		root.getChildren().addAll(pane, submit, blue, red, yellow, black, eraser, promptBase, wordPrompt, errorLabel);

		// creates and shows the window
		primaryStage.setTitle("Drawing Application");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void newConnection(String name) {
	
	}

	@Override
	public void lostConnection(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageToServer(String text) {
		// TODO Auto-generated method stub

	}

}

package drawComp;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.stage.Stage;
import java.util.Stack;

public class DrawingFace extends Application {

	public void start(Stage primaryStage) throws Exception {

		// objects
		Pane root = new Pane(); // base for all layouts
		StackPane pane = new StackPane(); // holds the canvas
		Canvas canvas = new Canvas(350, 390); // canvas
		DrawingTools tool = new DrawingTools();
		Stack undoHistory = new Stack();

		Button btnClear = new Button("Clear"); // clear button
		Button btnUndo = new Button("Undo"); // clear button

		btnUndo.setLayoutX(100);

		// colour palate objects
		Rectangle blue = new Rectangle(30, 30);
		Rectangle red = new Rectangle(30, 30);
		Rectangle yellow = new Rectangle(30, 30);
		Rectangle black = new Rectangle(30, 30);

		// GraphicsContext
		GraphicsContext gc;
		gc = canvas.getGraphicsContext2D();

		// configuring the canvas
		pane.setStyle("-fx-background-color: white"); // sets canvas colour
		pane.setLayoutX(50); // find a way to center horizontally without doing it manually!!
		pane.setLayoutY(70);

		// set up eraser
		Image eraserImage = new Image("file:src/drawComp/eraser.png"); // loads image
		ImageView eraser = new ImageView();
		eraser.setImage(eraserImage);
		eraser.setFitWidth(50);
		eraser.setPreserveRatio(true);
		eraser.setLayoutX(340);
		eraser.setLayoutY(480);

		// set up brush
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);

		// set up palate
		blue.setFill(Color.BLUE);
		blue.setLayoutX(55);
		blue.setLayoutY(480);

		red.setFill(Color.RED);
		red.setLayoutX(95);

		red.setLayoutY(480);

		yellow.setFill(Color.YELLOW);
		yellow.setLayoutX(135);
		yellow.setLayoutY(480);

		black.setFill(Color.BLACK);
		black.setLayoutX(175);
		black.setLayoutY(480);

		// event handlers

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

		// stop path 
		canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				gc.lineTo(event.getX(), event.getY());
				gc.stroke();
				gc.closePath();
			}
		});
		
		// clears the entire canvas
		btnClear.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				gc.clearRect(0, 0, 350, 390);
			}
		});

		// undo the last action
		btnUndo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				gc.clearRect(0, 0, 350, 390);
			}
		});

		// set up scene
		Scene scene = new Scene(root, 450, 550);
		root.setBackground(new Background(new BackgroundFill(Color.POWDERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

		// adding all GUI elements
		pane.getChildren().addAll(canvas);
		root.getChildren().addAll(pane, btnClear, blue, red, yellow, black, eraser, btnUndo);

		// creates and shows the window
		primaryStage.setTitle("Drawing Application");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String args[]) {
		launch(args);
	}

}

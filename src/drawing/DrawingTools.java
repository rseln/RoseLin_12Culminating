package drawing;

//Rose Lin 
//Mr. Radulovic
//June 18th, 2019
//Culminating Project: DrawingTools (methods all pertain towards drawing elements for the drawing Gui)

import javafx.scene.paint.*; 
import javafx.scene.canvas.*;

public class DrawingTools {

	public void brush(String colour, GraphicsContext gc) {
		gc.setStroke(colourSelector(colour));
		gc.setLineWidth(2);
	}

	public void eraser(GraphicsContext gc) {
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(20);
	}

	public Color colourSelector(String colour) {
		if (colour == "black")
			return Color.BLACK;

		if (colour == "yellow")
			return Color.YELLOW;
		
		if (colour == "blue")
			return Color.BLUE;
		
		if (colour == "red")
			return Color.RED;
		
		return Color.BLACK;
	}

}
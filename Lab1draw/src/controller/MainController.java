package controller;
/*HI2011, Laboration i designmönster
 * Robert Scott & Dainel Östberg
 * 2017-03
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class MainController implements Initializable {
	@FXML MenuController menuController;
	@FXML FormatController formatController;
	@FXML ShapeMenuController shapeMenuController;
	@FXML BorderPane MainView;
	@FXML CanvasController canvasController;
	@FXML AnchorPane root;

	// Variables to resize screen
	static Canvas c;
	static AnchorPane toolbar;
	
	private double rWidth = 0;
	
	// Resize Canvas
	private void resizeCanvas(){
		// Lookup
		rWidth = ((AnchorPane)root.lookup("#shapeMenu")).getWidth();
		
		c.setWidth(toolbar.getWidth()-rWidth);
		GraphicsContext gc = c.getGraphicsContext2D();
    	gc.setFill(Color.WHITE);
    	gc.fillRect(0, 0, c.getWidth(), c.getHeight());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// TODO: Figure out the rest, get listener for height of toolbar and increase canvas size to fill
					// the rest, should also redraw canvas on resize FUN TIMES!!!
					
		// Get canvas, can probably make it look better
		c = (Canvas) ((AnchorPane)root.lookup("#canvas")).getChildren().get(0);
		toolbar = ((AnchorPane)root.lookup("#format"));
		
		
		// Set a listener to track window-size TODO: should listen to root
		toolbar.widthProperty().addListener(o -> resizeCanvas());
		
		MediatorController.getInstance().registerController(menuController);
		MediatorController.getInstance().registerController(formatController);
		MediatorController.getInstance().registerController(shapeMenuController);
		MediatorController.getInstance().registerController(canvasController);
	}
}

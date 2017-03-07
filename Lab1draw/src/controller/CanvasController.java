package controller;
/*HI2011, Laboration i designm�nster
 * Robert Scott & Dainel �stberg
 * 2017-03
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.pojo.FormattingObject; 

public class CanvasController implements Initializable {
	@FXML
	Canvas canvas;
//	private UndoRedoFactory factory;;
	private FormattingObject formatObject;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		final GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.AQUAMARINE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

	//	factory = new UndoRedoFactory();
		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
		
				gc.setFill(formatObject.getColour());
				gc.fillRect(e.getX() - 2, e.getY() - 2, 20, 20);
		//		factory.addAction(formatObject);
			
		});
	}
/*
	public FormattingObject undoAction() {
	//	return factory.undoAction();
	}
*/
	public void add(FormattingObject formattingClass) {
		this.formatObject = formattingClass;

	}

}
package controller;
/*HI2011, Laboration i designmönster
 * Robert Scott & Dainel Östberg
 * 2017-03
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
	@FXML MenuController menuController;
	@FXML FormatController formatController;
	@FXML ShapeMenuController shapeMenuController;
	@FXML BorderPane MainView;
	@FXML CanvasController canvasController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MediatorController.getInstance().registerController(menuController);
		MediatorController.getInstance().registerController(formatController);
		MediatorController.getInstance().registerController(shapeMenuController);
		MediatorController.getInstance().registerController(canvasController);
	}
}

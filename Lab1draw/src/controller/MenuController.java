package controller;
/*HI2011, Laboration i designmönster
 * Robert Scott & Dainel Östberg
 * 2017-03
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;


public class MenuController implements Initializable{
	@FXML private ToolBar ToolBar;

	public void exitProgram(){
		 System.exit(0);
	}

	public void delete(){
		//System.out.println(MediatorController.getInstance().controllertestgetObject().toStringAll());
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		
	}
}

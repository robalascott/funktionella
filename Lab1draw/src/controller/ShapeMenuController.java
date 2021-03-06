package controller;
/*HI2011, Laboration i designmönster
 * Robert Scott & Dainel Östberg
 * 2017-03
 */
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import model.pojo.ShapeType;
import model.shapes.ShapeFactory;

public class ShapeMenuController implements Initializable {
	@FXML private VBox ShapeMenu;
	private ShapeType shapeType;
	private ToggleGroup toggleGroup = new ToggleGroup();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ToggleButton btn = new ToggleButton();

		ShapeMenu.setPadding(new Insets(5, 5, 5, 5));
		ShapeMenu.setSpacing(5);

		ShapeFactory.getAvailableShapes();
		for(String shape : ShapeFactory.getAvailableShapes()){
			btn = new ToggleButton(shape);
			btn.setPrefSize(100, 50);
			btn.setToggleGroup(toggleGroup);
			btn.setUserData(shape);
			ShapeMenu.getChildren().add(btn);
		}
		/*
		for (int i = 0; i < 8; i++) {
			btn = new ToggleButton("Button" + i);
			btn.setPrefSize(100, 50);
			btn.setToggleGroup(toggleGroup);
			btn.setUserData("Button" + i);
			ShapeMenu.getChildren().add(btn);
		};
		*/
		toggleGroup.selectedToggleProperty().addListener(E -> {
			try{
				this.shapeType.setShapeName(toggleGroup.getSelectedToggle().getUserData().toString());
			}catch(NullPointerException exception){
				System.out.println("Do nothing");
			}
		
		});
		this.shapeType = new ShapeType(null);
	}

	public ShapeType ObjectShapeClass() {
		return this.shapeType ;
	}
	private ShapeType ObjectShape() {
		return new ShapeType(toggleGroup.getSelectedToggle().getUserData().toString());
	}
}

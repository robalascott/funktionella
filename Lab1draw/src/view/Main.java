package view;
/*HI2011, Laboration i designmönster
 * Robert Scott & Dainel Östberg
 * 2017-03
 */


import java.awt.Graphics2D;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	final static String imageicon =  "/view/images/temp1.png";
	final static String title = "Stampz";
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle(title);
			primaryStage.getIcons().add(new Image(imageicon));
			Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
			
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

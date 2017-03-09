package controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/*HI2011, Laboration i designmönster
 * Robert Scott & Dainel Östberg
 * 2017-03
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.shapes.Shape;
import model.shapes.UndoCommand;
import model.shapes.UndoInvoker;


public class MenuController implements Initializable{
	@FXML private HBox ToolBar;
	private UndoInvoker invoker = UndoInvoker.getInstance();

	public void exitProgram(){
		 System.exit(0);
	}

	public void delete(){
		//System.out.println(MediatorController.getInstance().controllertestgetObject().toStringAll());
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		
	}
	
	@FXML
	public void save(ActionEvent event){
		
		TextInputDialog dialog = new TextInputDialog("Save Name");
		dialog.setTitle("Save");
		dialog.setHeaderText("Save File");
		dialog.setContentText("Enter save file name:");
		dialog.setGraphic(new ImageView(this.getClass().getResource("/view/images/save-icon.png").toString()));
		
		Optional<String> result = dialog.showAndWait();
		
		result.ifPresent(filename -> {
			String regex = "^[a-zA-z0-9]+$";
			if(filename.matches(regex)){
				System.out.println("Valid name");
			
				try {
			         FileOutputStream fileOut =
			         new FileOutputStream("./saves/" + filename + ".ser");
			         ObjectOutputStream out = new ObjectOutputStream(fileOut);
			         
		        	 out.writeObject(MediatorController.getInstance().getShapeList());
			         out.close();
			         fileOut.close();
			         System.out.printf("Serialized data is saved in tmp/" + filename + ".ser");
			      }catch(IOException i) {
			         i.printStackTrace();
			      }
			}else{
				System.out.println("Invalid filename");
			}
		});
	}
	
	@FXML
	@SuppressWarnings("unchecked")
	public void load(){
		
		
		TextInputDialog dialog = new TextInputDialog("Filename");
		dialog.setTitle("Load");
		dialog.setHeaderText("Load File");
		dialog.setContentText("Enter filename:");
		dialog.setGraphic(new ImageView(this.getClass().getResource("/view/images/save-icon.png").toString()));
		
		
		Optional<String> result = dialog.showAndWait();
		
		result.ifPresent(filename -> {
			try{
				FileInputStream fileIn = new FileInputStream("./saves/" + filename +".ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				//Object obj = in.readObject();
				 
				// Can check if instanceof arraylist then itterate through
				// and check if all elements are shapes, kinda overkill 
				// though
				
				ArrayList<Shape> buffer = (ArrayList<Shape>) in.readObject();
				MediatorController.getInstance().loadSaveFile(buffer);
			
				//shapes = (ArrayList<Shape>) in.readObject();
				//shapes = (ObservableList<Shape>) in.readObject();
				in.close();
				fileIn.close();
				//drawShapes();
		      }catch(ClassNotFoundException c) {
		         System.out.println("Class not found");
		         c.printStackTrace();
		         return;
		      }catch(FileNotFoundException e){
		    	  Alert alert = new Alert(AlertType.WARNING);
		    	  alert.setHeaderText("File not found!");
		    	  alert.setContentText(filename);
		    	  alert.showAndWait();
		      }catch(InvalidClassException e){
		    	  Alert alert = new Alert(AlertType.WARNING);
		    	  alert.setTitle("Error reading file");
		    	  alert.setHeaderText("Invalid or corrupt save-file!");
		    	  alert.showAndWait();
		      }catch (IOException e) {
		    	  System.out.println("Something went wrong reading file");
		    	  e.printStackTrace();
		      }
		});	
		
	}
}

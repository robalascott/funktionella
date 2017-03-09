package controller;
/*HI2011, Laboration i designmönster
 * Robert Scott & Dainel Östberg
 * 2017-03
 */

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.paint.Color;
import model.pojo.FormattingObject;
import model.pojo.ShapeType;
import model.shapes.Shape;
import model.shapes.UndoInvoker;

public class MediatorController implements MediatorControllerInterface, Observer{

	private MenuController menuController;
	private FormatController formatController;
	private ShapeMenuController shapeMenuController;
	private CanvasController canvasController;
	

	/*Observable Class*/
	private MediatorController() {
	}

	public static MediatorController getInstance() {
		return MediatorControllerHolder.INSTANCE;
	}

	private static class MediatorControllerHolder {
		private static final MediatorController INSTANCE = new MediatorController();
	}

	@Override
	public void registerController(MenuController menuController) {
		this.menuController = menuController;

	}

	@Override
	public void registerController(FormatController formatController) {
		this.formatController = formatController;
		
		if(this.formatController != null){
			formatController.getFormattingClass().addObserver(this);	
		}
		
		
	}
	@Override
	public void registerController(CanvasController canvasController) {
		this.canvasController = canvasController;
	}

	@Override
	public void registerController(ShapeMenuController shapeMenuController) {
		this.shapeMenuController =  shapeMenuController;
		if(this.shapeMenuController  != null){
			this.shapeMenuController.ObjectShapeClass().addObserver(this);
			System.out.println(shapeMenuController.ObjectShapeClass().toStringAll());	
		}
	}
	


	@Override
	public void update(Observable obs, Object arg1) {
		if(obs instanceof FormattingObject ){
			System.out.println(this.formatController.getFormattingClass().toStringAll());
			this.canvasController.add(this.formatController.getFormattingClass());
//			UndoRedoManager.INSTANCE.add(this.formatController.getFormattingClass());
			
			//Send formating object to canvas here
		}
		if(obs instanceof ShapeType ){
			System.out.println(this.shapeMenuController.ObjectShapeClass().toStringAll());
			this.canvasController.addShape(this.shapeMenuController.ObjectShapeClass());
			//Send formating object to canvas here
		}
	}

	public void loadSaveFile(ArrayList<Shape> list){
		this.canvasController.load(list);
	}
	
	public ArrayList<Shape> getShapeList(){
		return this.canvasController.getShapes();
	}
	
	@Override
	public FormattingObject getFormattingClass() {
		return null;
	}

	@Override
	public ShapeType ObjectNameClass() {
		return null;
	}

	@Override
	public void undo() {
		if(UndoInvoker.getInstance().execute()){
			this.formatController.setDisableUndo(true);
		}
		//FormattingObject formatObject = new FormattingObject(Color.ANTIQUEWHITE,10,"Yes");
		//FormattingObject formatObject = manager.undo(); 
		//System.out.println(formatObject.toStringAll());
		//FormattingObject formatObject =  this.canvasController.undoAction();
		//this.formatController.loader(formatObject);	
	}



}

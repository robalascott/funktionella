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



public class MediatorController implements MediatorControllerInterface, Observer,Classes {

	private MenuController menuController;
	private FormatController formatController;
	private ShapeMenuController shapeMenuController;
	private CanvasController canvasController;
	
	private MediatorController() {
	}

	public static MediatorController getInstance() {
		return MediatorControllerHolder.INSTANCE;
	}

	private static class MediatorControllerHolder {
		private static final MediatorController INSTANCE = new MediatorController();
	}

	@Override
	public void registerController(Object e) {
		System.out.println(e.getClass().getTypeName());

		switch (e.getClass().getTypeName()) {
		case menu:
			this.menuController = (MenuController) e;
			break;
		case format:
			this.formatController = (FormatController) e;
			formatController.getFormattingClass().addObserver(this);
			break;
		case canvas:
			this.canvasController = (CanvasController) e;
			break;
		case shape:
			this.shapeMenuController = (ShapeMenuController) e;
			this.shapeMenuController.ObjectShapeClass().addObserver(this);
			System.out.println(shapeMenuController.ObjectShapeClass().toStringAll());
			break;
		default:
			System.out.println("Failed to load: " + e.toString());
		}

	}

	@Override
	public void update(Observable obs, Object arg1) {
		if (obs instanceof FormattingObject) {
			System.out.println(this.formatController.getFormattingClass().toStringAll());
			this.canvasController.add(this.formatController.getFormattingClass());
			// UndoRedoManager.INSTANCE.add(this.formatController.getFormattingClass());

			// Send formating object to canvas here
		}
		if (obs instanceof ShapeType) {
			System.out.println(this.shapeMenuController.ObjectShapeClass().toStringAll());
			this.canvasController.addShape(this.shapeMenuController.ObjectShapeClass());
			// Send formating object to canvas here
		}
	}

	public void deleteSelectedShapes(){ 
		this.canvasController.delete();
	}
	
	public void copySelectedShapes(){
		this.canvasController.copy();
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
		UndoInvoker.getInstance().execute();
	

	}

}

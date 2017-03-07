package controller;
/*HI2011, Laboration i designmönster
 * Robert Scott & Daniel Östberg
 * 2017-03
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.pojo.FormattingObject;
import model.pojo.ShapeType;
import model.shapes.Shape;
import model.shapes.ShapeFactory;

public class CanvasController implements Initializable {
	private GraphicsContext gc;
	@FXML private Canvas canvas;
	//private ArrayList<Shape> shapes;
	private ArrayList<Shape> selected;
	
	private ObservableList<Shape> shapes;
	//private ObservableList<Shape> selected;
	 
	private double startX, startY;
//	private UndoRedoFactory factory;;
	private FormattingObject formatObject;
	private ShapeType shapeType;

	 @Override
		public void initialize(URL location, ResourceBundle resources) {
	    	// Get the graphic context of Canvas to draw on
		 
	    	gc = canvas.getGraphicsContext2D();
	    	gc.setFill(Color.AQUAMARINE);
	    	gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	    	
	    	// We don't need selected to be watched since all selected elements should
	    	// also be in the list of all shapes. With the java.util.observer we would 
	    	// have declared an array of shapes.
	    	// shapes = new ArrayList<Shape>();
	    	selected = new ArrayList<>();
	    	
	    	// If we use this instead of Observable we need to define all values
	    	// to be watched in a Observable array, we do this in the extractor in the 
	    	// shape, we set a flag for changed, just like setChanged() and calling 
	    	// notifyObservers() with the java.util.observer class. This solution tracks
	    	// if elements are added and removed to the array aswell, we don't have to 
	    	// call notify everytime we change the variable BUT we need the special 
	    	// javafx-class for the property instead.
	    	shapes = FXCollections.observableArrayList(Shape.extractor());
	    	
	    	// If we would have extended this class with Observer instead, we would have
	    	// added it as an observer to every Shape we added manually like this
	    	// line.addObserver(this);
	    	
	    	// The listener for when the observableArrayList is triggered
	    	shapes.addListener((ListChangeListener<Shape>)e -> {
	    		// Some event was triggered, while there is another event
	    		while(e.next()){
	    			// If the event is an updated property
	    			if(e.wasUpdated()){
	    				// Should never be more than one in our program
	    				// but just in case we go through all changed elements
	    				for(int i = e.getFrom(); i < e.getTo(); ++i){
	    					// If flag wasChanged; clear it
	    					if(shapes.get(i).getStatus())
	    						shapes.get(i).clearChanged();
	    				}
	    			}
	    		}
	    		// Redraw shapes since something was changed
				System.out.println("Change in list");
				clearDrawShapes();	
	    	});
	    	//System.out.println("Got type: " + test.get(0).getType());
	    	//test.get(0).getType();
	    	//line.getType();
	    	//test.add(line);
	    	//System.out.println(test.toString());
	    	
	    	canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					System.out.println("Click: x: " + event.getX() + " y: " +event.getY());
					boolean deselect = true;
					for(Shape s : shapes){
						// Look through our place shapes, if
						// any shape contains the clicked coordinates
						// do something
						if(s.contains(event.getX(), event.getY())){
							// Do something
							deselect = false;
							if(!selected.contains(s)){
								selected.add(s);
								System.out.println("Added one shape to selection");
								
								for(Shape selected : selected)
									selected.startMoveEvent(event.getX(),event.getY());
									
								System.out.println("Startx: " + startX
										+ " StartY: " + startY);
								// We only want to select one shape at a time, and not all
								// the stacked ones
								return;
							}							
						}
					}
					// If the click was on the empty canvas, deselect shapes
					if(deselect){
						selected = new ArrayList<Shape>();
					}else{
						// Update all selected shapes with the location clicked
						for(Shape s : selected)
							s.startMoveEvent(event.getX(), event.getY());
					}
				}    		
	    	});
	    	
	    	// Should deselect when changing tool from select tool
	    	canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent event) {
					if(selected.isEmpty()){
						event.consume();
						return;
					}
					//System.out.println("X: " + event.getX() +  "Y: " + event.getY());
					// Can you reshape a bunch of shapes at the same time?
					// Im guessing NO, mby you should be able to scale them instead
					if(selected.size() == 1){
						// Do we want to reshape or move the shape
						// TODO: Should the controller be informed or just reshape
						// when the shape wants to?
						Shape e = selected.get(0);
						if(!e.reshape(event.getX(), event.getY()))
							e.move(event.getX(), event.getY());
					}else{
						// If several selected, we should only move them?
						for(Shape s : selected){
							s.move(event.getX(),event.getY());
							//s.reshape(event.getX(), event.getY());
						}
					}
					//gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					//drawShapes();
				}
	    		
	    	});
	 }
/*
	public FormattingObject undoAction() {
	//	return factory.undoAction();
	}
*/
	// Clear canvas and draw all shapes on it
	public void clearDrawShapes(){
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		drawShapes();
	}
	   
	// Draw shapes
    public void drawShapes(){
    	for(Shape s : shapes){
    		s.draw(gc);
    	}
    }
	// The formating for the selected shapes were changed, perform changes and
	// redraw. TODO: Color is not observed, if we change color the class wont be redrawn
	public void add(FormattingObject formattingClass) {
		this.formatObject = formattingClass;
		for(Shape s : selected){
			s.setColor(formattingClass.getColour());
		}
		clearDrawShapes();
	}
	
	// No sure how we want to place a shape, it we just want a default position
	// we only need one function that just spawn them, otherwise we might want to
	// spawn them relative to a click-location
	public void addShape(ShapeType shape) {
		this.shapeType = shape;
		AddShape();
	}
	
    public void AddShape(){
    	System.out.println("Adding shape: " + shapeType.getShapeName());
    	shapes.add(ShapeFactory.getShape(shapeType.getShapeName()));
    }
    
    // Load function called by Mediator that replace the Shapes on the Canvas
    public void load(ArrayList<Shape> loadedShapes){
    	shapes.clear();
		for(Shape s : loadedShapes){
		 shapes.add(s);
		}
    }
    
    // Return an Array representing all the shapes on the canvas, effectivly
    // a copy of the ObservableArrayList
	public ArrayList<Shape> getShapes() {
		ArrayList<Shape> save = new ArrayList<>();
        for(Shape s : shapes){
       	 save.add(s);
        }
        return save;
	}
}

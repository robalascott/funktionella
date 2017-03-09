package model.shapes;
/*HI2011, Laboration i designmönster
 * Robert Scott & Daniel Östberg
 * 2017-03
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

//import java.util.Observable;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.pojo.FormattingObject;

// TODO: Think through if there is an alternate solution to moving
// the shape relative to its starting position, (without calling 
// startMoveEvent()), that would be cool

// TODO: Add Move to front kinda functionality - focus selected mby

// If we would have used the java.util.Observable, we would have extended shape with ti
public abstract class Shape implements Cloneable, Serializable{
	// Default properties for a shape
	protected double x = 50,y = 50;
	protected transient Color color = Color.BLACK;
	protected boolean fill = false;
	protected int strokewidth = 5;
	protected boolean selected = false;
	
	// Observable value (JavaFx property), Flag to indicate that the shape was altered
	protected static SimpleBooleanProperty changed = new SimpleBooleanProperty(false);
	
	/*Abstract methods that should be implemented when extending shape*/
	// Draw the shape on the given context
	abstract public void draw(GraphicsContext gc);	
	// We want to alter the shape
	abstract public boolean reshape(double x, double y);
	abstract String getType();
	
	// Check if the given coordinates lies within the shape
	// Currently sets the selectable reshape point
	// not sure what the best approach is here
	abstract public boolean contains(double x, double y);
	
	
	public void setSelected(boolean selected){
		this.selected = selected;
		changed.set(true);
	};
	
	public void setFill(boolean b){
		this.fill = b;
	}
	
	public boolean isFilled(){
		return fill;
	}
	
	public void setFormat(FormattingObject format){
		color = format.getColour();
		if(format.getFill().equals("YES")){
			fill = true;
		}else{
			fill = false;
			strokewidth = format.getWidth();
		}
	}
	
	// I don't like that we have to define a method to setup observer
	// although i think this is the equivalent of calling setChanged() 
	// and notifyObservers().
	public static Callback<Shape, Observable[]> extractor(){
		return (Shape s ) -> new Observable[]{changed};
	}
	
	/* // When we tested with java.util.Observable this was called to notify the observers
	protected void update(){
		setChanged();
		notifyObservers();
	}
	*/
	
	public boolean getStatus(){
		return changed.get();
	}
	
	public void clearChanged(){
		changed.set(false);
	}
	
	public void setCoordinates(double x, double y){
		this.x = x;
		this.y = y;
		changed.set(true);;
		//update();
	}
	
	public void move(double currentMouseX, double currentMouseY){
		// Might want to check if out of bounds?
		this.x = shapeStartX + currentMouseX-mouseStartX;
		this.y = shapeStartY + currentMouseY-mouseStartY;
		changed.set(true);
		//update();
		//System.out.println("Moved to: " + this.x + ", " + this.y);
	}
	
	
	public void setColor(Color color){
		this.color = color;
		changed.set(true);
	}
	public Color getColor(){
		return color;
	}	

	// Maby make abstract so individual shape only get "their stuff"
	// When setting other startvalues in contains for example, there
	// might be a slight difference in startlocation; causing the shape
	// to "jump/move" when starting a reshape 
	protected double shapeStartX, shapeStartY, mouseStartX, mouseStartY;
	public void startMoveEvent(double mouse_x, double mouse_y) {
		shapeStartX = x;
		shapeStartY = y;
		mouseStartX = mouse_x;
		mouseStartY = mouse_y;
	}
	
	/*==============Serialisation==============//
	 * We need to extend the regular serialisation since the
	 * Color (JavaFx property) is unserializable
	 *=========================================*/
	private void writeObject(ObjectOutputStream s) throws IOException{
		 s.defaultWriteObject();
		 s.writeDouble(color.getRed());
         s.writeDouble(color.getGreen());
         s.writeDouble(color.getBlue());
         s.writeDouble(color.getOpacity());
         
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject();
		this.color = new Color(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble());
	}
	//=========================================//
	
	// Clone a shape
	// TODO: Handle CloneNotSupportedException
	@Override
	public Shape clone(){
		try {
			return (Shape) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}

package model.shapes;
/*HI2011, Laboration i designmönster
 * Robert Scott & Daniel Östberg
 * 2017-03
 */
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// TODO: figure out best way to move shape with hitbox
public class Oval extends Shape{

	private static final long serialVersionUID = -7879757298053408718L;

	private double width = 50, height = 20;
	Rectangle boundingBox;
	Ellipse2D E;
	
	public Oval(){
		boundingBox = new Rectangle(50,20);
		boundingBox.setHitBox(true);
	}	
	
	@Override
	public boolean contains(double pX, double pY) {
		
		// Sort of cheating but who cares
		Point2D A = new Point2D.Double(pX,pY);
		E = new Ellipse2D.Double(x, y, width, height);
		
		if(selected)
			if(boundingBox.contains(pX, pY))
				return true;
						
		if(E.contains(A)){
			return true;
		}
		
		return false;
	}

	@Override
	String getType() {
		return "Oval";
	}

	// We want to move the shape & boundingBox relative to the
	// initial location of the mouseclick
	@Override
	public void startMoveEvent(double mouse_x, double mouse_y) {
		super.startMoveEvent(mouse_x, mouse_y);
		boundingBox.startMoveEvent(mouse_x, mouse_y);
	}
	
	// When we move the oval we also want to move the boundingBox
	@Override
	public void move(double x, double y){
		super.move(x, y);
		boundingBox.move(x, y);
		//System.out.println("Move: " + boundingBox.getX()+ "," + boundingBox.getY() + ","+ x +","+  y);
	}
	
	@Override
	public boolean reshape(double PointX, double PointY) {
		if(boundingBox.reshape(PointX,PointY)){
			// Match shape size to boundingBox
			width = boundingBox.width;
			height = boundingBox.height;
			
			// Match shape location to boundingBox
			x = boundingBox.x;
			y = boundingBox.y;
			changed.set(true);
			return true;
		}
		return false;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// If selected, draw the rectangle representing the boudingBox
		if(selected){
			boundingBox.setLineWidth(5);
			boundingBox.setFill(false);
			boundingBox.draw(gc);
		}
		if(isFilled()){
			gc.setFill(color);
			gc.fillOval(boundingBox.getX(),boundingBox.getY(),boundingBox.getWidth(),boundingBox.getHeight());
			
		}else{
			gc.setStroke(color);
			gc.strokeOval(x, y, width, height);
		}
	}
	
	@Override
	public Shape clone() {
		// Not sure how we can clone the other objects
		Oval clone = (Oval)super.clone();
		Rectangle BB = (Rectangle)boundingBox.clone();
		clone.boundingBox = BB;
		//clone.setSelected(false);
		clone.selected = false;
		// I don't understand this
		// SetSelected uses this, causing problems
		// clone.changed.set(newValue);
		return clone;
		//return new Oval(BB, this);
	}
}

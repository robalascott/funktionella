package model.shapes;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javafx.scene.canvas.GraphicsContext;

public class Oval extends Shape{

	private static final long serialVersionUID = -7879757298053408718L;

	private double width = 50, height = 20;
	@Override
	public boolean contains(double pX, double pY) {
		
		// Sort of cheating but who cares
		Point2D A = new Point2D.Double(pX,pY);
		Ellipse2D E = new Ellipse2D.Double(x, y, width, height);
		
		if(E.contains(A))
			return true;
		
		return false;
	}

	@Override
	String getType() {
		return "Oval";
	}

	@Override
	public boolean reshape(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(GraphicsContext gc) {
		if(isFilled()){
			gc.setFill(color);
			gc.fillOval(x, y, width, height);
		}else{
			gc.setStroke(color);
			gc.strokeOval(x, y, width, height);
		}
	}

}

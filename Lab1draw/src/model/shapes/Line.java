package model.shapes;

import java.awt.event.MouseEvent;

import com.sun.javafx.geom.Line2D;

import javafx.beans.InvalidationListener;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Shape{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9055652183992812239L;
	
	private enum MovePoint{STARTPOINT, ENDPOINT, NONE};
	private MovePoint selected = MovePoint.NONE;

	double x1 = 0, y1 = 0, x2 = 50, y2 = 50;
	// Might want different margins for side and continuation of line
	// and mby another for reshape clicks
	double MARGIN = 5;
	Line(){
	}
	
	Line(double startx, double starty, double endx, double endy){
		
	}
	
	@Override
	public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.setLineWidth(5);
        gc.strokeLine(x+x1,y+y1,x+x2,y+y2);
	}

	@Override
	public boolean reshape(double clickX, double clickY) {
		switch(selected){
			case STARTPOINT:
				x1 = clickX-x;
				y1 = clickY-y;
				//update();
				changed.set(true);
				return true;
			case ENDPOINT:
				x2 = clickX-x;
				y2 = clickY-y;
				//update();
				changed.set(true);
				return true;
			case NONE: 
				return false;
		default:
			return false;
		}
	}

	@Override
	public boolean contains(double clickX, double clickY) {
		
		
		if(Math.abs(clickX-(x+x1)) < MARGIN && Math.abs(clickY-(y+y1)) < MARGIN){
			selected = MovePoint.STARTPOINT;
			return true;
		}
		if(Math.abs(clickX-(x+x2)) < MARGIN && Math.abs(clickY-(y+y2)) < MARGIN){
			selected = MovePoint.ENDPOINT;
			return true;
		}
		
		selected = MovePoint.NONE;
		// Cross-product
		//double cross = (clickX-x1)*(y2 - clickY) - (x2 - clickX)*(clickY - y1);
		double cross = ((clickX-(x+x1))*((y+y2) - clickY) - ((x+x2) - clickX)*(clickY - (y+y1)));
		
		// Distance from Point A to Point B on line
		double distance = Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2),2));
		
		// Distance from Click to Line, return false if click was to far away
		if(Math.abs(cross/distance) > MARGIN)
			return false;
		
		System.out.println("Point is on line (with margin)");
		
		// Check if the click was between the endpoints of the line
		// Take margin into consideration so that clicks outside shape is 
		// accepted  (Mby this should be an option, what if shapes are
		// very close to eachother?)
		
		// System.out.println("StartPoint: " + (x1+x) + " Endpoint: " + (x2+x) + " ClickX: " + clickX);
		if(Math.abs(x1-x2) >= Math.abs(y1-y2)){
			if((x2-x1) > 0){
				return x1+x-MARGIN <= clickX && clickX <= x2+x+MARGIN;
			}else{
				return x2+x-MARGIN <= clickX && clickX <= x1+x+MARGIN;
			}
		}else{
			if((y2-y1) > 0){
				return y1+y-MARGIN <= clickY && clickY <= y2+y+MARGIN;
			}else{
			    return y2+y-MARGIN <= clickY && clickY <= y1+y+MARGIN;
			}
		}
	}

	@Override
	String getType() {
		return "Line";
	}
		
	/*
	@Override
	public Line clone(){
		try {
			return (Line)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	*/


}

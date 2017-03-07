package model.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape{
	
	private static final long serialVersionUID = 9105021260299256230L;

	private enum MovePoint{NE_CORNER, NW_CORNER, SE_CORNER, SW_CORNER, NONE};
	private MovePoint selected = MovePoint.NONE;
	private double startWidth, startHeight;

	double width = 50,height = 50;
	// Might want different margins for side and continuation of line
	// and mby another for reshape clicks
	double MARGIN = 5;
	
	public Rectangle() {
		// TODO Auto-generated constructor stub
	}
	
	public Rectangle(double width, double height){
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(x,y,width,height);
	}

	private double relativeX = 0, relativeY = 0;
	@Override
	public boolean reshape(double clickX, double clickY) {
		switch(selected){
			case NE_CORNER:
				
				changed.set(true);
				return true;
			case NW_CORNER:
				// Invert to lower left
				if((startHeight + (mouseStartY-clickY)) < 0){
					// If we define an ofset and move the mouse starting point we 
					// can use the same logic as if we clicked there from the begining
					// and we don't have to define logic relative to all sides for all 
					// sides, *phew*
					relativeY += startHeight;
					mouseStartY = y+startHeight;
					selected = MovePoint.SW_CORNER;
					/*
					y = shapeStartX+startHeight;
					x = clickX;
					width = startWidth+(mouseStartX-clickX);
					height = -(startHeight+(mouseStartY-clickY));
					*/
				}else if(startWidth + (mouseStartX-clickX) < 0){
					System.out.println("OK");
					// Logic goes here
					relativeX += startWidth;
					mouseStartX = x+startWidth;
					selected = MovePoint.NE_CORNER;
					
				// Original side NW_CORNER
				}else{
					x = clickX;
					y = clickY;
					width = startWidth+(mouseStartX-clickX);
					height = startHeight+(mouseStartY-clickY);
					
				}
				System.out.println("NW Corner");
				changed.set(true);
				return true;
			case SE_CORNER:
				changed.set(true);
				return true;
			case SW_CORNER:
				/* Left side
				x = shapeStartX-(mouseStartX-clickX);
				y = shapeStartY;
				width = startWidth+(mouseStartX-clickX);
				height = startHeight;
				*/
				System.out.println("x: " + x + "y: " + y + "StartHeight: " + startHeight);
				x = shapeStartX+relativeX-(mouseStartX-clickX);
				y = shapeStartY+relativeY;
				width = startWidth+(mouseStartX-clickX);
				height = startHeight-(mouseStartY-clickY);
				
				changed.set(true);
				return true;
			case NONE: 
				return false;
		default:
			return false;
		}
	}

	private boolean isMovePoint(double xAxel, double yAxel){
		startHeight = height;
		startWidth = width;
		if(Math.abs(xAxel - x) < MARGIN && Math.abs(yAxel-y) < MARGIN){
			selected = MovePoint.NW_CORNER;
			return true;
		}
		if(Math.abs(xAxel - (x+width)) < MARGIN && Math.abs(yAxel-y) < MARGIN){
			selected = MovePoint.NE_CORNER;
			return true;
		}
		if(Math.abs(xAxel - x) < MARGIN && Math.abs(yAxel-(y+height)) < MARGIN){
			selected = MovePoint.SW_CORNER;
			return true;
		}
		if(Math.abs(xAxel - (x+width)) < MARGIN && Math.abs(yAxel-(y+height)) < MARGIN){
			selected = MovePoint.SE_CORNER;
			return true;
		}
		selected = MovePoint.NONE;
		return false;
	}
	// TODO: What was it Anders said about many if:s?
	@Override
	public boolean contains(double clickX, double clickY) {
		
		if(isMovePoint(clickX, clickY))
			return true;
			
		if(clickX > x && clickX < x+width && clickY > y && clickY < y+height){
			System.out.println("Within square");
			return true;
		}
		return false;
	}

	@Override
	String getType() {
		return "Square";
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

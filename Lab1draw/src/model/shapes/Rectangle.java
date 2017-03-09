package model.shapes;

import javafx.scene.canvas.GraphicsContext;
import model.pojo.FormattingObject;

public class Rectangle extends Shape{
	
	private static final long serialVersionUID = 9105021260299256230L;

	private enum MovePoint{NE_CORNER, NW_CORNER, SE_CORNER, SW_CORNER, NONE};
	private MovePoint selected = MovePoint.NONE;
	private double startWidth, startHeight;

	double width = 50,height = 50;
	double MARGIN = 5;
	
	public Rectangle(){
		
	}
	
	public Rectangle(double width, double height){
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		if(fill){
			gc.setFill(color);
			gc.fillRect(x,y,width,height);
		}else{
			//gc.setLineWidth(strokewidth);
			// TODO: Fix this the proper way, was just lazy
			if(hitBox){
				gc.setLineDashes(2.5d);
			}else{
				//gc.setLineWidth(strokewidth);
			}
			gc.setStroke(color);
			gc.strokeRect(x, y, width, height);
			gc.setLineDashes(0);
		}
	}

	private double difX = 0, difY=0;
	
	// TODO: Something is wrong, atleast one of the crossovers are a bit glitchy 
	// causing the shape to "jump" away because of bad values, FIXED: but it's still
	// a bit glitchy when moving diagonally fast, sometimes the shape is not updated 
	// properly, crossover not updating width height is my guess, not that important though
	
	// TODO: For fun, could add reshape-points on the sides and not only corners
	@Override
	public boolean reshape(double clickX, double clickY) {
		difX = mouseStartX-clickX;
		difY = mouseStartY-clickY;
		switch(selected){
			case NE_CORNER:
				// We moved to SW Quadrant
				if(startWidth-difX < 0 && startHeight+difY < 0){
					System.out.println("From NE to SW");
					shapeStartX = shapeStartX-startWidth;
					shapeStartY = shapeStartY+startHeight;
					mouseStartX = shapeStartX;
					mouseStartY = shapeStartY+startHeight;
					selected = MovePoint.SW_CORNER;
				// We moved to NW Quadrant
				}else if(startWidth-difX < 0){
					System.out.println("From NE to NW");
					System.out.println("Shapestart: " + shapeStartX + ", MouseStartX: " + mouseStartX);
					mouseStartX = shapeStartX-startWidth;
					shapeStartX = mouseStartX;
					selected = MovePoint.NW_CORNER;
					
					System.out.println("Shapestart: " + shapeStartX + ", MouseStartX: " + mouseStartX);
				// We moved to SE Quadrant
				}else if(startHeight+difY < 0){
					shapeStartY = shapeStartY+startHeight;
					mouseStartY = shapeStartY+startHeight;
					//mouseStartY = shapeStartY+startHeight*2;
					selected = MovePoint.SE_CORNER;
				}else{
					//System.out.println("StartHeight: " + startHeight + ", DifY: " + difY);
					y = shapeStartY-(mouseStartY-clickY);
					x = shapeStartX;
					height = startHeight+(mouseStartY-clickY);
					width = startWidth-(mouseStartX-clickX);
				}
				changed.set(true);
				return true;
			case NW_CORNER:
				// We moved to SE Quadrant
				if((startHeight + (mouseStartY-clickY)) < 0 && startWidth + (mouseStartX-clickX) < 0){
					System.out.println("From NW to SE");
					shapeStartY = shapeStartY+startHeight;
					mouseStartY = shapeStartY+startHeight;
					shapeStartX = shapeStartX+startWidth;
					mouseStartX = shapeStartX+startWidth;
					selected = MovePoint.SE_CORNER;
				// We moved to SW Quadrant
				}else if((startHeight + (mouseStartY-clickY)) < 0){
					mouseStartY = shapeStartY+startHeight*2;
					shapeStartY = shapeStartY+startHeight;
					selected = MovePoint.SW_CORNER;
				// We moved to the NE Quadrant
				}else if(startWidth + (mouseStartX-clickX) < 0){
					shapeStartX = shapeStartX+startWidth;
					mouseStartX = shapeStartX+startWidth;
					selected = MovePoint.NE_CORNER;
				// Reforming Shape on this Quadrant
				}else{
					x = clickX;
					y = clickY;
					width = startWidth+(mouseStartX-clickX);
					height = startHeight+(mouseStartY-clickY);
				}
				changed.set(true);
				return true;
			case SE_CORNER:
				if(difY > startHeight && difX > startWidth){
					System.out.println("From SE to NW");
					shapeStartY -= startHeight;
					shapeStartX -= startWidth;
					mouseStartY = shapeStartY-startHeight;
					mouseStartX = shapeStartX-startWidth;
					selected = MovePoint.NW_CORNER;
				}else if(difY > startHeight){
					shapeStartY -= startHeight;
					mouseStartY = shapeStartY;
					selected = MovePoint.NE_CORNER;
				}else if(difX > startWidth){
					shapeStartX -= startWidth;
					mouseStartX = shapeStartX;
					selected = MovePoint.SW_CORNER;
				}else{
					x = shapeStartX;
					y = shapeStartY;
					width = startWidth-(mouseStartX-clickX);
					height = startHeight-(mouseStartY-clickY);
				}
				changed.set(true);
				return true;
			case SW_CORNER:
				// We moved to the NW Quadrant
				if(difX < -startWidth && (startHeight - (mouseStartY-clickY)) < 0){
					System.out.println("From SW to NE");
					shapeStartX += startWidth;
					mouseStartX = shapeStartX +startWidth;
					mouseStartY = shapeStartY-startHeight;
					shapeStartY = shapeStartY-startHeight;
					selected = MovePoint.NE_CORNER;
				}else if(difX < -startWidth){
					shapeStartX += startWidth;
					mouseStartX = shapeStartX +startWidth;
					selected = MovePoint.SE_CORNER;
				}else if((startHeight - (mouseStartY-clickY)) < 0){
					mouseStartY = shapeStartY-startHeight;
					shapeStartY = shapeStartY-startHeight;
					selected = MovePoint.NW_CORNER;
				}else{x = shapeStartX-(mouseStartX-clickX);
					y = shapeStartY;
					x = shapeStartX-difX;
					width = startWidth+(mouseStartX-clickX);
					height = startHeight-(mouseStartY-clickY);
				}
				changed.set(true);
				return true;
			case NONE: 
				return false;
		default:
			return false;
		}
	}

	/* Left side
	x = shapeStartX-(mouseStartX-clickX);
	y = shapeStartY;
	width = startWidth+(mouseStartX-clickX);
	height = startHeight;
	*/
	@Override
	public void startMoveEvent(double mouse_x, double mouse_y) {
		super.startMoveEvent(mouse_x, mouse_y);
		System.out.println("Setting vals in rec");
		startHeight = height;
		startWidth = width;
	}
	
	private boolean isMovePoint(double xAxel, double yAxel){
		
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
	
	public MovePoint getSelected(){
		return selected;
	}

	@Override
	String getType() {
		return "Rectangle";
	}
	
	/******TEST*********/
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getWidth(){
		return width;
	}
	public double getHeight(){
		return height;
	}
	
	private boolean hitBox = false;
	
	public void setHitBox(boolean b){
		hitBox = b;
	}
	
	// TODO: Change to double
	public void setLineWidth(int w){
		strokewidth = w;
	}
}

package model.shapes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Should have "repo" of prototyp shapes that are cloned upon request
public abstract class ShapeFactory {
	public final static List<String> validShapes = Arrays.asList("Line","Rectangle");
	
	public static List<Shape> prototypeShapes = getShapeList();
	
	private static List<Shape> getShapeList(){
		if(prototypeShapes == null){
			prototypeShapes = new ArrayList<>();
		
		// Create & add all prototypes
		Line pLine = new Line();
		prototypeShapes.add(pLine);
		Rectangle pRec = new Rectangle();
		prototypeShapes.add(pRec);
		
		}
		return prototypeShapes;
	}
	
	public static List<String> getAvailableShapes(){
		 return validShapes;
	}
	
	public static Shape getShape(String shape){
		switch(shape.toLowerCase()){
			case "line":
				System.out.println("Want Line");
				return prototypeShapes.get(0).clone();
			case "rectangle":
				System.out.println("Want Rectangle");
				return prototypeShapes.get(1).clone();
			default:
				return null;
		}
	}
}

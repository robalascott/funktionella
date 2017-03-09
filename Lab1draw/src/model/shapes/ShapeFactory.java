package model.shapes;
/*HI2011, Laboration i designmönster
 * Robert Scott & Daniel Östberg
 * 2017-03
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Should have "repo" of prototyp shapes that are cloned upon request
public abstract class ShapeFactory {
	public final static List<String> validShapes = Arrays.asList("Line","Rectangle","Oval");
	
	public static List<Shape> prototypeShapes = getShapeList();
	
	private static List<Shape> getShapeList(){
		if(prototypeShapes == null){
			prototypeShapes = new ArrayList<>();
		
		// Create & add all prototypes
		Line pLine = new Line();
		prototypeShapes.add(pLine);
		Rectangle pRec = new Rectangle();
		prototypeShapes.add(pRec);
		Oval pOval = new Oval();
		prototypeShapes.add(pOval);
		
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
			case "oval":
				System.out.println("Want oval");
				return prototypeShapes.get(2).clone();
			default:
				return null;
		}
	}
}

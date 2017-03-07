package model.pojo;
/*HI2011, Laboration i designm�nster
 * Robert Scott & Dainel �stberg
 * 2017-03
 */
import java.util.Observable;

public class ShapeType extends Observable{
	private String shapeName;

	public ShapeType(String objectName){
		this.shapeName = objectName;
	}
	
	public String getShapeName() {
		return shapeName;
	}

	public void setShapeName(String objectName) {
		this.shapeName = objectName;
		setChanged();
	    notifyObservers();
	}
	public String toStringAll(){
		return shapeName;
	}
}

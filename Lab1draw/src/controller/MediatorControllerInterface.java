package controller;
/*HI2011, Laboration i designm�nster
 * Robert Scott & Dainel �stberg
 * 2017-03
 */
import model.pojo.FormattingObject;
import model.pojo.ShapeType;


public interface MediatorControllerInterface {
	  void registerController(Object e);	
	  FormattingObject getFormattingClass();
	  ShapeType ObjectNameClass(); 
	  void undo();
}

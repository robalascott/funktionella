package controller;
/*HI2011, Laboration i designmönster
 * Robert Scott & Dainel Östberg
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

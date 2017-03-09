package controller.undo;
/*HI2011, Laboration i designmönster
 * Robert Scott & Dainel Östberg
 * 2017-03
 */
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import model.shapes.Shape;

public interface Command {

	public void undo();
	public void redo();
	public class UndoAdd implements Command {

		private Shape s;
		private ObservableList<Shape> listRef;
		
		public UndoAdd(Shape s, ObservableList<Shape> list){
			this.s = s;
			listRef = list;
		}
		
		@Override
		public void undo() {
			listRef.remove(s);
		}

		@Override
		public void redo() {
			listRef.add(s);
		}
	}


	public class UndoDelete implements Command {
	
		@Override
		public void undo() {
			System.out.println("Running undo delete");
			
		}

		@Override
		public void redo() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class UndoLoad implements Command{

		@Override
		public void undo() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void redo() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class UndoChangeColor implements Command{
		
		private Shape s;
		private Color c;
		private Color old;
		
		public UndoChangeColor(Shape shape, Color originalColor){
			s = shape;
			old = s.getColor();
			c = originalColor;
		}
		
		@Override
		public void undo() {
			old = s.getColor();
			s.setColor(c);
		}

		@Override
		public void redo() {
			s.setColor(old);
			
		}
		
	}
	

	
}

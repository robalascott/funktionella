package model.shapes;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public interface UndoCommand {

	public void undo();
	
	public class UndoAdd implements UndoCommand {

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

	}

	public class UndoDelete implements UndoCommand {
	
		@Override
		public void undo() {
			System.out.println("Running undo delete");
			
		}
		
	}
	
	public class UndoLoad implements UndoCommand{

		@Override
		public void undo() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class UndoChangeColor implements UndoCommand{
		
		private Shape s;
		private Color c;
		
		public UndoChangeColor(Shape shape, Color originalColor){
			s = shape;
			c = originalColor;
		}
		
		@Override
		public void undo() {
			System.out.println("Running undo color: " + c.toString());
			s.setColor(c);
		}
		
	}
}

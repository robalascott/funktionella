package controller.undo;
/*HI2011, Laboration i designmönster
 * Robert Scott & Daniel Östberg
 * 2017-03
 */
import java.util.ArrayList;

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

		public UndoAdd(Shape s, ObservableList<Shape> list) {
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

		private Shape shapeRef;
		private ObservableList<Shape> listRef;
		private int tmp = -1, index = 0;

		public UndoDelete(Shape s, ObservableList<Shape> list) {
			if ((tmp = list.indexOf(s)) != -1) {
				index = tmp;
			}
			shapeRef = s;
			listRef = list;
		}

		@Override
		public void undo() {
			listRef.add(index, shapeRef);
		}

		@Override
		public void redo() {
			listRef.remove(shapeRef);
			// fix this
		}

	}

	public class UndoLoad implements Command {

		@Override
		public void undo() {
			// TODO Auto-generated method stub

		}

		@Override
		public void redo() {
			// TODO Auto-generated method stub

		}

	}

	public class UndoChangeColor implements Command {

		private Shape s;
		private Color c;
		private Color old;

		public UndoChangeColor(Shape shape, Color originalColor) {
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

	public class UndoGroupDelete implements Command {

		private ArrayList<Shape> sortedGroup = new ArrayList<>();
		private ObservableList<Shape> listRef;
		private ArrayList<Integer> index = new ArrayList<>();

		public UndoGroupDelete(ArrayList<Shape> group, ObservableList<Shape> list) {
			for (Shape s : group) {
				if (sortedGroup.isEmpty()) {
					System.out.println("List is empty adding: " + list.indexOf(s));
					sortedGroup.add(s);
				} else {
					if (list.indexOf(s) > list.indexOf(sortedGroup.get(sortedGroup.size() - 1))) {
						sortedGroup.add(s);
						System.out.println("Larger than all elements adding: " + list.indexOf(s));
					} else if (list.indexOf(s) < list.indexOf(sortedGroup.get(0))) {
						sortedGroup.add(0, s);
						System.out.println("Smaller than all elements adding: " + list.indexOf(s));
					} else {
						for (Shape sorted : sortedGroup) {
							if (list.indexOf(sorted) > list.indexOf(s)) {
								sortedGroup.add(sortedGroup.indexOf(sorted), s);
								System.out.println("Inserting: " + list.indexOf(s) + " to spot: "
										+ (sortedGroup.indexOf(sorted) - 1));
								break;
							}
						}
					}
				}
			}

			for (int i = 0; i < sortedGroup.size(); i++) {
				index.add(i, list.indexOf(sortedGroup.get(i)));
			}
			listRef = list;
		}

		@Override
		public void undo() {
			for (int i = 0; i < sortedGroup.size(); i++) {
				System.out.println(index.get(i));
				listRef.add(index.get(i), sortedGroup.get(i));
			}
		}

		@Override
		public void redo() {

		}
	}

}

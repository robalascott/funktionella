package controller.undo;
/*HI2011, Laboration i designmönster
 * Robert Scott & Dainel Östberg
 * 2017-03
 */
import java.util.EmptyStackException;
import java.util.Observable;
import java.util.Stack;
import controller.MediatorController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class UndoInvoker extends Observable {
	private static Stack<Command> undostack = new Stack<Command>();
	private static Stack<Command> redostack = new Stack<Command>();
	private static UndoInvoker invoker = new UndoInvoker();

	private BooleanProperty undoIsEmpty = new SimpleBooleanProperty();
	private BooleanProperty redoIsEmpty = new SimpleBooleanProperty();

	private UndoInvoker() {
		// Set listener
		undoIsEmpty.set(true);
		redoIsEmpty.set(true);
		// Listener for buttons
		undoIsEmpty.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				MediatorController.getInstance().resetUndo(newValue);
			}
		});
		redoIsEmpty.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				MediatorController.getInstance().resetRedo(newValue);
				System.out.println("redo");
			}
		});
	}

	public static UndoInvoker getInstance() {
		return invoker;
	}

	public void addUndoCommand(Command command) {
		undostack.push(command);
		undoIsEmpty.set(false);
		redostack.clear();
		redoIsEmpty.setValue(true);
	}

	public void Undoexecute() {
		try {
			Command command = undostack.pop();
			command.undo();
			this.pushtoRedo(command);
			undostack.peek();
		} catch (EmptyStackException e) {
			undoIsEmpty.set(true);
		}
	}

	public void Redoexecute() {
		try {
			Command command = redostack.pop();
			command.redo();
			undostack.push(command);
			undoIsEmpty.set(false);
			undostack.peek();
			if(redostack.isEmpty()){
				redoIsEmpty.set(true);
			}
		} catch (EmptyStackException e) {
			redoIsEmpty.set(true);
		}
	}
	
	private void pushtoRedo(Command command) {
		redostack.push(command);
		redoIsEmpty.set(false);

	}
}

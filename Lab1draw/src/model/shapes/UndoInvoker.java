package model.shapes;

import java.util.EmptyStackException;
import java.util.Stack;

import controller.MediatorController;

public class UndoInvoker {
	private static Stack<UndoCommand> undostack = new Stack<UndoCommand>();
	private static UndoInvoker invoker = new UndoInvoker();
	
	private boolean undoIsEmpty = true;
	private boolean redoIsEmpty = true;
	
	private UndoInvoker(){}
	
	public static UndoInvoker getInstance(){
		return invoker;
	}
	
	public void addUndoCommand(UndoCommand command){
		undostack.push(command);
		undoIsEmpty = false;
	}
	
	public void execute(){
		try{
			undostack.pop().undo();
			undostack.peek();
		}catch(EmptyStackException e){
			undoIsEmpty = true;
		}
	}
}

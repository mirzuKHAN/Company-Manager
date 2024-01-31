import java.util.ArrayList;

public class CmdStartNewDay extends RecordedCommand {

	private String last = SystemDate.getInstance().toString();
	private String now;
	private ArrayList<Leave> toRemove;

	@Override
	public void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
			ArrayIndexOutOfBoundsException, ExEmployeeNotFound {
		now = cmdParts[1];
		SystemDate.getInstance().set(now);

		toRemove = new ArrayList<>();
		for (Leave l : Company.getInstance().getAllLeaves()) {
			if (l.getEnd().getNum() < SystemDate.getInstance().getNum()) {
				toRemove.add(l);
			}
		}
		for (Leave l : toRemove) {
			Company.getInstance().removeLeave(l);
		}

		addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
								// RecordedCommand.java)
		clearRedoList(); // <====== There maybe some commands stored in the redo list. Clear them.

		System.out.println("Done.");
	}

	@Override
	public void undoMe() {
		for (Leave l : toRemove) {
			Company.getInstance().addLeave(l);
		}
		SystemDate.getInstance().set(last);
		addRedoCommand(this); // <====== upon undo, we should keep a copy in the redo list (addRedoCommand is
								// implemented in RecordedCommand.java)
	}

	@Override
	public void redoMe() {
		for (Leave l : toRemove) {
			Company.getInstance().removeLeave(l);
		}
		SystemDate.getInstance().set(now);
		addUndoCommand(this); // <====== upon redo, we should keep a copy in the undo list
	}
}

public class CmdHire extends RecordedCommand // <=== note the change
{
	// When addSalary is invoked, an object of the AddSalary class will be
	// added to the undo list (and may also be stored in the redo list later)
	// We add instance fields in the objects to store the data which will be needed
	// upon undo/redo
	private Employee e;
	private Company company;

	@Override
	public void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
			ArrayIndexOutOfBoundsException, ExEmployeeNotFound {
		// Note: In Q1, e and addAmount were local variables. But now they are the data
		// in the object (instance fields).
		company = Company.getInstance();
		try {
			e = company.findEmployee(cmdParts[1]);
			System.out.println("Employee already exists!");
			return;
		} catch (ExEmployeeNotFound e) {
		}

		try {
			e = new Employee(cmdParts[1], Integer.parseInt(cmdParts[2]));

			company.addEmployee(e);

			addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
									// RecordedCommand.java)
			clearRedoList(); // <====== There maybe some commands stored in the redo list. Clear them.

			System.out.println("Done.");
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format for annual leaves!");
			return;
		}
	}

	@Override
	public void undoMe() {
		company.removeEmployee(e);
		addRedoCommand(this); // <====== upon undo, we should keep a copy in the redo list (addRedoCommand is
								// implemented in RecordedCommand.java)
	}

	@Override
	public void redoMe() {
		company.addEmployee(e);
		addUndoCommand(this); // <====== upon redo, we should keep a copy in the undo list
	}
}
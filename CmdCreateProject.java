public class CmdCreateProject extends RecordedCommand // <=== note the change
{
	// When addSalary is invoked, an object of the AddSalary class will be
	// added to the undo list (and may also be stored in the redo list later)
	// We add instance fields in the objects to store the data which will be needed
	// upon undo/redo
	private Project p;
	private Company company;

	@Override
	public void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
			ArrayIndexOutOfBoundsException, ExEmployeeNotFound {
		company = Company.getInstance();
		try {
			p = company.findProject(cmdParts[1]);
			System.out.println("Project already exists!");
			return;
		} catch (ExProjectNotFound e) {
		}

		try {
			p = new Project(cmdParts[1], cmdParts[2], Integer.parseInt(cmdParts[3]));

			company.addProject(p);

			addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
									// RecordedCommand.java)
			clearRedoList(); // <====== There maybe some commands stored in the redo list. Clear them.

			System.out.println("Done.");

		} catch (NumberFormatException e) {
			System.out.println("Wrong number format for project duration!");
			return;
		}
	}

	@Override
	public void undoMe() {
		company.removeProject(p);
		addRedoCommand(this); // <====== upon undo, we should keep a copy in the redo list (addRedoCommand is
								// implemented in RecordedCommand.java)
	}

	@Override
	public void redoMe() {
		company.addProject(p);
		addUndoCommand(this); // <====== upon redo, we should keep a copy in the undo list
	}
}
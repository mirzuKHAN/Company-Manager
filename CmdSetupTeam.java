public class CmdSetupTeam extends RecordedCommand {

	private Company company;
	private Employee employee;
	private Team t;

	@Override
	public void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
			ArrayIndexOutOfBoundsException, ExEmployeeNotFound {
		company = Company.getInstance();
		try {
			t = company.findTeam(cmdParts[1]);
			System.out.println("Team already exists!");
			return;
		} catch (ExTeamNotFound e) {
		}

		employee = company.findEmployee(cmdParts[2]);

		if (employee.getTeam() != null) {
			System.out
					.println(employee.getName() + " has already joined another team: " + employee.getTeam().getName());
			return;
		}

		t = new Team(cmdParts[1], employee);

		company.addTeam(t);

		addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
								// RecordedCommand.java)
		clearRedoList(); // <====== There maybe some commands stored in the redo list. Clear them.

		System.out.println("Done.");
	}

	@Override
	public void undoMe() {
		company.removeTeam(t);
		addRedoCommand(this); // <====== upon undo, we should keep a copy in the redo list (addRedoCommand is
								// implemented in RecordedCommand.java)
	}

	@Override
	public void redoMe() {
		company.addTeam(t);
		addUndoCommand(this); // <====== upon redo, we should keep a copy in the undo list
	}
}

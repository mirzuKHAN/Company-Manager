public class CmdJoinTeam extends RecordedCommand {

	private Team team;
	private Company company;
	private Employee employee;

	@Override
	public void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
			ArrayIndexOutOfBoundsException, ExEmployeeNotFound {

		company = Company.getInstance();
		team = company.findTeam(cmdParts[2]);
		employee = company.findEmployee(cmdParts[1]);

		if (employee.getTeam() != null) {
			System.out
					.println(employee.getName() + " has already joined another team: " + employee.getTeam().getName());
			return;
		}

		team.addMember(employee);

		addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
								// RecordedCommand.java)
		clearRedoList(); // <====== There maybe some commands stored in the redo list. Clear them.

		System.out.println("Done.");
	}

	@Override
	public void undoMe() {
		team.removeMember(employee);
		addRedoCommand(this); // <====== upon undo, we should keep a copy in the redo list (addRedoCommand is
								// implemented in RecordedCommand.java)
	}

	@Override
	public void redoMe() {
		team.addMember(employee);
		addUndoCommand(this); // <====== upon redo, we should keep a copy in the undo list
	}

}

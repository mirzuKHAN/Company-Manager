public class CmdAssign extends RecordedCommand {

	private Company company;
	private Team team;
	private Project project;

	@Override
	public void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
			ArrayIndexOutOfBoundsException, ExEmployeeNotFound {
		company = Company.getInstance();
		project = company.findProject(cmdParts[1]);
		team = company.findTeam(cmdParts[2]);

		if (project.getAssignedTeam() != null) {
			System.out.println("This project is already assigned to the team: " + project.getAssignedTeam());
		}
		team.addProject(project);

		addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
								// RecordedCommand.java)
		clearRedoList(); // <====== There maybe some commands stored in the redo list. Clear them.

		System.out.println("Done.");
	}

	@Override
	public void undoMe() {
		team.removeProject(project);
		addRedoCommand(this); // <====== upon undo, we should keep a copy in the redo list (addRedoCommand is
								// implemented in RecordedCommand.java)
	}

	@Override
	public void redoMe() {
		team.addProject(project);
		addUndoCommand(this); // <====== upon redo, we should keep a copy in the undo list
	}

}

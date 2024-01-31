public class CmdListTeamMembers implements Command {
    @Override
    public void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
            ArrayIndexOutOfBoundsException, ExEmployeeNotFound {
        Company company = Company.getInstance();
        Team team = company.findTeam(cmdParts[1]);
        company.listMembers(team);
    }
}

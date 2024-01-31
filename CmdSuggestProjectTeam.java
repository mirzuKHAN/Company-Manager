public class CmdSuggestProjectTeam implements Command {
    @Override
    public void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
            ArrayIndexOutOfBoundsException, ExEmployeeNotFound {
        Company company = Company.getInstance();

        Project p = company.findProject(cmdParts[1]);

        company.suggestProjectTeam(p);
    }
}

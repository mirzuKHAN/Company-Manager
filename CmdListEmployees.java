public class CmdListEmployees implements Command {
    @Override
    public void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
            ArrayIndexOutOfBoundsException, ExEmployeeNotFound {
        Company company = Company.getInstance();

        company.listEmployees();
    }
}

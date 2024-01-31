public class CmdListLeaves implements Command {
    @Override
    public void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
            ArrayIndexOutOfBoundsException, ExEmployeeNotFound {
        Company company = Company.getInstance();
        try {
            Employee employee = company.findEmployee(cmdParts[1]);
            company.listLeaves(employee);
        } catch (ArrayIndexOutOfBoundsException e) {
            company.listLeaves();
        }

    }
}

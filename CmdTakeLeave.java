public class CmdTakeLeave extends RecordedCommand {

    private Company company;
    private Employee employee;
    private Leave leave;

    @Override
    public void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
            ArrayIndexOutOfBoundsException, ExEmployeeNotFound {
        company = Company.getInstance();
        employee = company.findEmployee(cmdParts[1]);

        Day start = new Day(cmdParts[2]);
        Day end = new Day(cmdParts[3]);
        int duration = end.substract(start) + 1;

        if (duration > employee.getAnnualLeaveCopy()) {
            System.out.println(
                    "Insufficient balance of annual leave. " + employee.getAnnualLeaveCopy() + " days left only!");
            return;
        }

        if (employee.getTeam() != null) {
            for (Project p : employee.getTeam().getProjects()) {
                if ((p.getEndDay().getNum() > end.getNum()
                        && p.getEndDay().substract(end) + 1 <= Math.min(p.getDuration(), 5)) ||
                        (p.getEndDay().getNum() > start.getNum()
                                && p.getEndDay().substract(start) + 1 <= Math.min(p.getDuration(), 5))
                        ||
                        (p.getEndDay().getNum() >= start.getNum() && p.getEndDay().getNum() <= end.getNum())) {

                    System.out.println("The leave is invalid.  Reason: Project " + p.getName() +
                            " will be in its final stage during "
                            + p.getEndDay().add(-Math.min(p.getDuration() - 2, 3)).toString() + " to "
                            + p.getEndDay().toString() + ".");
                    return;
                }
            }
        }

        for (Leave l : company.getAllLeaves()) {
            if (employee != l.getEmployee())
                continue;
            if ((l.getStart().getNum() <= start.getNum() && start.getNum() <= l.getEnd().getNum()) ||
                    (l.getStart().getNum() >= start.getNum() && end.getNum() >= l.getStart().getNum())) {
                System.out.println("Leave overlapped: The leave period " + l.getStart().toString() +
                        " to " + l.getEnd().toString() + " is found!");
                return;
            }
        }

        leave = new Leave(employee, start, end);

        company.addLeave(leave);

        addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
                              // RecordedCommand.java)
        clearRedoList(); // <====== There maybe some commands stored in the redo list. Clear them.

        System.out.println("Done. " + employee.getName() + "'s remaining annual leave: " + employee.getAnnualLeaveCopy()
                + " days\n");
    }

    @Override
    public void undoMe() {
        company.removeLeave(leave);
        ;
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        company.addLeave(leave);
        addUndoCommand(this);
    }

}

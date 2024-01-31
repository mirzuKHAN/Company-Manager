interface Command {
    void execute(String[] cmdParts) throws NumberFormatException, ExTeamNotFound, ExProjectNotFound,
            ArrayIndexOutOfBoundsException, ExEmployeeNotFound;
}

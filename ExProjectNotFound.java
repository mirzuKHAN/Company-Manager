public class ExProjectNotFound extends Exception {
    public ExProjectNotFound() {
        super("Project not found!");
    }

    public ExProjectNotFound(String message) {
        super(message);
    }
}
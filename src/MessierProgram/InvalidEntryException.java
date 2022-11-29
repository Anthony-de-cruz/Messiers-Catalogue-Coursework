package src.MessierProgram;

/**
 * Thrown when an argument is in an invalid format.
 */
public class InvalidEntryException extends Exception {
    public InvalidEntryException(String message) {
        super(message);
    }
}

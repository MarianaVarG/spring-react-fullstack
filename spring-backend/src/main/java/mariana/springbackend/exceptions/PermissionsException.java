package mariana.springbackend.exceptions;

public class PermissionsException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public PermissionsException(String message) {
        super(message);
    }
}

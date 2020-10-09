package pl.polsl.workflow.manager.server.exception;

public abstract class BaseException extends RuntimeException {

    protected BaseException(String message) {
        super(message);
    }

}

package pl.polsl.workflow.manager.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends BaseException {

    public NotAuthorizedException(String message) {
        super(message);
    }

}

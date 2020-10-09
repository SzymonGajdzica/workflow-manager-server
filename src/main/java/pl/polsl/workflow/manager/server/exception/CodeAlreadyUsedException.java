package pl.polsl.workflow.manager.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CodeAlreadyUsedException extends BaseException {

    public CodeAlreadyUsedException(String code) {
        super("Code '" + code + "' already used");
    }

}

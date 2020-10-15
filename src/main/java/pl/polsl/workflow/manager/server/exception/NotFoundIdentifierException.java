package pl.polsl.workflow.manager.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundIdentifierException extends BaseException {

    public NotFoundIdentifierException(String code) {
        super("Could not found any match for code '" + code + "'");
    }

    public NotFoundIdentifierException(Long id) {
        super("Could not found any match for id '" + id + "'");
    }

    public NotFoundIdentifierException(List<Long> ids) {
        super("Could not found any match for ids [" + ids.stream().map(Object::toString).collect(Collectors.joining(",")) + "]");
    }

}

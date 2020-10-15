package pl.polsl.workflow.manager.server.helper.exception;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import pl.polsl.workflow.manager.server.exception.BaseException;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        Throwable error = getError(webRequest);
        if(error != null) {
            errorAttributes.put("exception", error.getClass().getSimpleName());
            errorAttributes.put("custom", error instanceof BaseException);
        }
        return errorAttributes;
    }
}
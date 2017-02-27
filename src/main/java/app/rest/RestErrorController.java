//http://stackoverflow.com/questions/25356781/spring-boot-remove-whitelabel-error-page
//http://stackoverflow.com/questions/32941917/remove-basic-error-controller-in-springfox-swaggerui
package app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import springfox.documentation.annotations.ApiIgnore;

/**
 * The RestErrorController automatically transforms any spring error pages that
 * could appear in to the form of JSON data.
 *
 * @author dion
 */
@ApiIgnore
@RestController
@RequestMapping("/error")
public class RestErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    /**
     *
     * @param errorAttributes
     */
    @Autowired
    public RestErrorController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    /**
     *
     * @return
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }

    /**
     * Maps the error attributes in to lines, to be returned as JSON.
     *
     * @param aRequest
     * @return
     */
    @RequestMapping
    public Map<String, Object> error(HttpServletRequest aRequest) {
        Map<String, Object> body = getErrorAttributes(aRequest, getTraceParameter(aRequest));
        String trace = (String) body.get("trace");
        if (trace != null) {
            String[] lines = trace.split("\n\t");
            body.put("trace", lines);
        }
        return body;
    }

    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(aRequest);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}

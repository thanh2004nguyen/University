package com.aptech.group3.Controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * This class is only required if you want to execute some custom logic in case of an error or if you want to display specific error pages in case of an error.
 */
@Controller
public class CustomErrorController implements ErrorController {

    private final Logger log = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        log.info("============Inside Custom Error Handler===========");
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            var statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                log.info("============404 Error Encountered===========");
                return "error-404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                log.info("============500 Error Encountered===========");
                return "error-500";
            }
            else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                log.info("============403 Error Encountered===========");
                return "error-403";
            }
        }
        return "error";
    }
    @RequestMapping("/403")
    public String handle403() {
        log.info("============403 Error Encountered===========");
        return "error-403";
    }
}
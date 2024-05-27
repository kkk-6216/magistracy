package com.pro.magistracy.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class ExceptionPageController implements ErrorController {

    private final Logger logger;

    public ExceptionPageController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        logger.info("Http status code >> " + statusCode);
        logger.info("Exception >> " + exception);

        Class<?> exceptionType = (Class<?>) request.getAttribute("javax.servlet.error.exception_type");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        String requestURI = (String) request.getAttribute("javax.servlet.error.request_uri");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");

        logger.info("ExceptionType >> " + exceptionType);
        logger.info("ErrorMessage >> " + errorMessage);
        logger.info("RequestURI >> " + requestURI);
        logger.info("ServletName >> " + servletName);

        model.addAttribute("statusCode", statusCode);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if(authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("admin", true);
        } else if(authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_STUDENT"))) {
            model.addAttribute("student", true);
        } else if(authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_RECTOR"))) {
            model.addAttribute("rector", true);
        } else if(authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_TEACHER"))) {
            model.addAttribute("teacher", true);
        }

        if(statusCode != null && Integer.valueOf(statusCode.toString()) == HttpStatus.NOT_FOUND.value()) {
            return "error/page404";
        } else {
            return "error/page500";
        }
    }


}

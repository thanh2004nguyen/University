package com.aptech.group3.Config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            System.out.println("Response has already been committed. Unable to redirect to " + redirectUrl);
            return;
        }
        response.sendRedirect(redirectUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isTeacher = false;
        boolean isStudent = false;
        boolean isEmployee = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("TEACHER")) {
                isTeacher = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("STUDENT")) {
                isStudent = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("EMPLOYEE")) {
                isEmployee = true;
                break;
            }
        }

        if (isTeacher || isStudent) {
            return "/";
        } else if (isEmployee) {
            return "/admin/testAdmin";
        } else {
            throw new IllegalStateException();
        }
    }
}

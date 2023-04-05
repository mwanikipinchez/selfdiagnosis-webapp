package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.config;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.security.CustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component

public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ADMIN")) {
            response.sendRedirect("/admindashboard");
        } else if(roles.contains("ROLE_PATIENT")){

            response.sendRedirect("/dashboard");
        } else if(roles.contains("ROLE_DOCTOR")){
            response.sendRedirect("/ddashboard");
        } else if(roles.contains("ROLE_PHARMACY")){
            response.sendRedirect("/pdashboard");
        }

        else {
            response.sendRedirect("/");
        }
    }
}

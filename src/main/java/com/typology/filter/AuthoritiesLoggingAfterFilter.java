package com.typology.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.typology.config.AppAuthenticationProvider;
import com.typology.config.EnvironmentProperties;

import java.io.IOException;
import java.util.logging.Logger;

public class AuthoritiesLoggingAfterFilter extends OncePerRequestFilter{//implements Filter {

    private final Logger LOG =
            Logger.getLogger(AuthoritiesLoggingAfterFilter.class.getName());   

	@Autowired
	private AppAuthenticationProvider authentication;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

    	System.out.println("AuthoritiesLoggingAfterFilter reached");
    	
//		Authentication authObj;
//
//		authObj = authentication.authenticate(new UsernamePasswordAuthenticationToken("noob3", "123abc"));
//		SecurityContextHolder.getContext().setAuthentication(authObj);
//				
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	//get current user details- once user is authenticated it will be stored inside the security context
//        
//        if (null != authentication) {
//            LOG.info("User " + authentication.getName() + " is successfully authenticated and " + "has the authorities " + authentication.getAuthorities().toString());
//        }
        
        chain.doFilter(request, response);
    }
    
    
    //skip this on registration
    @Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/api/" + EnvironmentProperties.getApiVersion() + "/register"); 
	}

}

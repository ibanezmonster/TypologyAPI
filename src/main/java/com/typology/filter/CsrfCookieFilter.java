package com.typology.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

public class CsrfCookieFilter extends OncePerRequestFilter {	//once per request at any cost- there are no guarantees spring won't rerun a filter

	private final Logger LOG = Logger.getLogger(CsrfCookieFilter.class.getName());
	
	
	//use doFilterInternal for OncePerRequestFilter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException 
    {    
    	System.out.println("In CsrfCookieFilter");
    	CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        
        if(null != csrfToken.getHeaderName()){
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }
        
        LOG.info("CSRF info: " + csrfToken.getHeaderName() + ", " + csrfToken.getParameterName() + ", " + csrfToken.getToken());
        
        filterChain.doFilter(request, response);
    }

}

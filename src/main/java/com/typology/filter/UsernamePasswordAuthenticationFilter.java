package com.typology.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.typology.config.AppAuthenticationProvider;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UsernamePasswordAuthenticationFilter implements Filter
{

	@Autowired
	private AppAuthenticationProvider authentication;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		Authentication authObj;
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String header = req.getHeader(AUTHORIZATION);
		
		StringBuilder requestBody = new StringBuilder();
		
		HttpSession session = req.getSession(false);
		SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");

		if (sci != null) {
		        UserDetails cud = (UserDetails) sci.getAuthentication().getPrincipal();
		        // do whatever you need here with the UserDetails
		}
		
		try {
			BufferedReader reader = request.getReader();
			String line;
			while((line = reader.readLine()) != null) {
				requestBody.append(line);
			}
			
			
			//authObj = authentication.authenticate(new UsernamePasswordAuthenticationToken(req.getParameter(user), req.getParameter(pwd));
			
			
			//SecurityContextHolder.getContext().setAuthentication(authObj);
			//System.out.println(authObj.getName());
		}
		
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		
        chain.doFilter(request, response);
	}
	
}

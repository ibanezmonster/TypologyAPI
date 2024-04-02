package com.typology.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.typology.config.AppAuthenticationProvider;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RequestValidationBeforeFilter implements Filter
{
	private final Logger LOG = Logger.getLogger(RequestValidationBeforeFilter.class.getName());

	public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
	private Charset credentialsCharset = StandardCharsets.UTF_8;
	
	

	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		//AUTHORIZATION header will be sent by UI application
		//Authorization Basic, Base 64 value of email/password
		String header = req.getHeader(AUTHORIZATION);				
		System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ" + header);

		
		if (header != null){
			header = header.trim(); // trim so no spaces in header value
			
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + header);

			if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)){ 
				// in angular, in interceptor class it's sending Authorization Basic header with email
				byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8); // starting from position 6, eliminates "Basic" text
				byte[] decoded;

				try{
					
					decoded = Base64.getDecoder().decode(base64Token);
					String token = new String(decoded, credentialsCharset);
					int delim = token.indexOf(":"); // delim: 21 for happytest@example.com:12345
	
					if (delim == -1){
						throw new BadCredentialsException("Invalid basic authentication token");
					}
	
					
					LOG.info("RequestValidationBeforeFilter");	
					
					//auth logic here
					

				}
				
				catch (IllegalArgumentException e){
					throw new BadCredentialsException("Failed to decode basic authentication token");
				}

			}

		}
		
		//header is null

		chain.doFilter(request, response);
	}
}

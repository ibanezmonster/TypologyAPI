package com.typology.filter;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.typology.constants.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter
{
	 private final Logger LOG = Logger.getLogger(JWTTokenGeneratorFilter.class.getName());
	 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
									throws ServletException, IOException, java.io.IOException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(null != authentication) {
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
			
			String jwt = Jwts.builder()
							.setIssuer("Typology API")
							.setSubject("JWT Token")
							.claim("username", authentication.getName())
							.claim("authorities", populateAuthorities(authentication.getAuthorities()))
							.setIssuedAt(new Date())
							.setExpiration(new Date((new Date()).getTime() + 30000000))		//expiration time of 8 hours
							.signWith(key)													//digitally sign by using secret key
							.compact();			
			
			LOG.info("JWT generated: " + jwt.toString());
			
			response.setHeader(SecurityConstants.JWT_HEADER, jwt);
		}
		
		filterChain.doFilter(request, response);
	}
	
	
	
	//the point of this is to only generate the JSON Web Token upon login
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/home");		//if current request is not /user, value will become true, meaning it should not filter. But for /user, it's executed
	}
	
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritiesSet = new HashSet<>();
		
		for(GrantedAuthority auth : collection) {
			authoritiesSet.add(auth.getAuthority());
		}
		
		return String.join(",", authoritiesSet);
	}
}

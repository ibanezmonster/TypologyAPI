package com.typology.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.typology.config.EnvironmentProperties;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class JWTTokenValidatorFilter extends OncePerRequestFilter
{
	 private final Logger LOG = Logger.getLogger(JWTTokenValidatorFilter.class.getName());
	 
	// validate by checking getting claims and storing in SecurityContextHolder
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		// trying to fetch the jwt token that my client application is sending in the request
		String jwt = request.getHeader(HttpHeaders.AUTHORIZATION); 
		
		//eliminate 'Bearer' prefix
		if(StringUtils.hasText(jwt) && jwt.startsWith("Bearer ")) {
			jwt = jwt.substring(7, jwt.length());
		}
		
		// you can set a breakpoint here and set the value of jwt to simulate token  tampering
		// if you do that, it will end up in catch block
		if (null != jwt)
		{
			try
			{
				SecretKey key = Keys.hmacShaKeyFor(EnvironmentProperties.getJWTSecretKey().getBytes(StandardCharsets.UTF_8)); // same  as in validation process

				Claims claims = Jwts.parserBuilder()
									.setSigningKey(key)
									.build()
									.parseClaimsJws(jwt) //internally try to calculate if hash value is matching // io.jsonwebtoken.io.DecodingException error
									.getBody(); // reading body values, don't need signature/header
												
				// using claims, fetch username, authorities to create UsernamePasswordAuthenticationToken
				String username = String.valueOf(claims.get("username"));
				//String role = (String) claims.get("role");
				String authorities = (String) claims.get("authorities");

				// at this point, telling spring authentication is successful
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

				// so store the auth in securitycontextholder
				SecurityContextHolder.getContext().setAuthentication(auth);
							
				LOG.info("Token Validator. Authorities: " + authorities);
			}
			
			catch(UnsupportedJwtException e) {
				System.out.println("Unsupported Jwt");
			}

			catch (Exception e)
			{ 
				// if token expired, will throw expiredjwtexception etc.
				throw new BadCredentialsException("Invalid Token received!" + e.getClass().getName());
			}
		}
		
		
		else {
			throw new BadCredentialsException("JWT is missing!");
		}
		

		filterChain.doFilter(request, response);
	}

	// has to be executed for all the APIs except during login/register operation
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request)
	{
		//String path = request.getServletPath();
		String path = request.getPathInfo();
		List<String> doNotFilterList = Arrays.asList("/api/" + EnvironmentProperties.getApiVersion() + "/register", 
													"/api/" + EnvironmentProperties.getApiVersion() + "/login",
													"/admin",
													"/h2");
		//System.out.println("fffffffffffffffffffffffffffffffff " + pathInfo);
		//return path.startsWith("localhost:8080/login");
		//return path.startsWith(pathInfo);
		return doNotFilterList.stream()
							  .anyMatch(n -> path.startsWith(n));
							  //.anyMatch(n -> path.startsWith("localhost:8080" + n));
	}

}

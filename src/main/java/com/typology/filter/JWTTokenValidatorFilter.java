package com.typology.filter;

import com.typology.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class JWTTokenValidatorFilter extends OncePerRequestFilter
{

	 private final Logger LOG = Logger.getLogger(JWTTokenValidatorFilter.class.getName());

	 
	// validate by checking getting claims and storing in SecurityContextHolder
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{

		String jwt = request.getHeader(SecurityConstants.JWT_HEADER); // trying to fetch the jwt token that my client application is sending in the request

		// you can set a breakpoint here and set the value of jwt to simulate token  tampering
		// if you do that, it will end up in catch block
		if (null != jwt)
		{
			try
			{
				SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8)); // same  as in validation process

				Claims claims = Jwts.parserBuilder()
									.setSigningKey(key)
									.build()
									.parseClaimsJws(jwt) // error here because it does have Claims
									.getBody(); // reading body values, don't need signature/header
				// using claims, fetch username, authorities to create
				// UsernamePasswordAuthenticationToken

				String username = String.valueOf(claims.get("username"));
				String authorities = (String) claims.get("authorities");

				// at this point, telling spring authentication is successful
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

				// so store the auth in securitycontextholder
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				LOG.info("Token Validator. Authorities: " + authorities);
			}

			catch (Exception e)
			{ 
				// if token expired, will throw expiredjwtexception etc.
				throw new BadCredentialsException("Invalid Token received!");
			}
		}

		filterChain.doFilter(request, response);
	}

	// has to be executed for all the APIs except during login operation
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request)
	{
		return request.getServletPath().equals("/register");
	}

}

package com.typology.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
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

import com.typology.jwt.JwtSecurityConstants;

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
		System.out.println("In JWTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
		
		
		// trying to fetch the jwt token that my client application is sending in the request
		String jwt = request.getHeader(JwtSecurityConstants.JWT_HEADER); 
		
		System.out.println("JWT issssssssssssssssssssss " + jwt);

		// you can set a breakpoint here and set the value of jwt to simulate token  tampering
		// if you do that, it will end up in catch block
		if (null != jwt)
		{
			System.out.println("not null! JWT issssssssssssssssssssss " + jwt);

			try
			{
				SecretKey key = Keys.hmacShaKeyFor(JwtSecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8)); // same  as in validation process

				Claims claims = Jwts.parserBuilder()
									.setSigningKey(key)
									.build()
									.parseClaimsJws(jwt) //internally try to calculate if hash value is matching // io.jsonwebtoken.io.DecodingException error
									.getBody(); // reading body values, don't need signature/header
												
				// using claims, fetch username, authorities to create UsernamePasswordAuthenticationToken
				String username = String.valueOf(claims.get("username"));
				String authorities = (String) claims.get("authorities");

				// at this point, telling spring authentication is successful
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

				// so store the auth in securitycontextholder
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				
				System.out.println("It was read correctly!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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

		filterChain.doFilter(request, response);
	}

	// has to be executed for all the APIs except during login operation
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request)
	{
		String path = request.getServletPath();
		List<String> doNotFilterList = Arrays.asList("/api/v1/register", "/api/v1/login");
		
		return doNotFilterList.stream()
							  .anyMatch(n -> n.equals(path));
	}

}

package com.typology.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.SecretKey;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.typology.config.EnvironmentProperties;
import com.typology.entity.user.AppUser;
import com.typology.user.UserDetailsImpl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWT
{
	private static final Logger logger = LoggerFactory.getLogger(JWT.class);

	public String getJwtFromCookies(HttpServletRequest request)
	{
		Cookie cookie = WebUtils.getCookie(request, EnvironmentProperties.getJWTTokenPrefix());

		if(cookie != null) {
			return cookie.getValue();
		}
		else {
			return null;
		}

	}

	public ResponseCookie generateJwtCookie(UserDetailsImpl user)
	{
		String jwt = generateTokenFromUsername(user.getUsername(), user.getAuthorities());
		ResponseCookie cookie = ResponseCookie.from(EnvironmentProperties.getJWTTokenPrefix(), jwt)
											  .path("/api/" + EnvironmentProperties.getApiVersion() + "/login")
											  .maxAge(24 * 60 * 60)
											  .httpOnly(true)
											  .build();
		return cookie;
	}
	
	public String generateTokenFromUsername(String userName, Collection<? extends GrantedAuthority> authorities)
	{
		SecretKey key = Keys.hmacShaKeyFor(EnvironmentProperties.getJWTSecretKey().getBytes(StandardCharsets.UTF_8));

		return Jwts.builder()
					.setIssuer("Typology API")
					.setSubject("JWT Token")
					.claim("username", userName)
					.claim("authorities", populateAuthorities(authorities))
					//.claim("role", role)
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + 30000000))		//expiration time of 8 hours
					.signWith(key)													//digitally sign by using secret key
					.compact();	
	}
	
	

	
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritiesSet = new HashSet<>();
		
		for(GrantedAuthority auth : collection) {
			authoritiesSet.add(auth.getAuthority());
		}
		
		return String.join(",", authoritiesSet);
	}
	
	

	public ResponseCookie getCleanJwtCookie()
	{
		ResponseCookie cookie = ResponseCookie.from(EnvironmentProperties.getJWTTokenPrefix(), null)
											  .path("/api/" + EnvironmentProperties.getApiVersion() + "/login")
											  .build();
		return cookie;
	}

	public String getUserNameFromJwtToken(String token)
	{
		return Jwts.parserBuilder()
					.setSigningKey(key())
					.build()
					.parseClaimsJws(token)
					.getBody()
					.getSubject();		
	}

	private Key key()
	{
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(EnvironmentProperties.getJWTSecretKey()));
	}
	
	
	

	public boolean validateJwtToken(String authToken)
	{

		try {
			Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parse(authToken);
			return true;
		}
		catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		}
		catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		}
		catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		}
		catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}

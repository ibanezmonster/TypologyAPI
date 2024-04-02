package com.typology.jwt;

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

import com.typology.entity.user.AppUser;
import com.typology.user.UserDetailsImpl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils
{
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${application.jwt.secretKey}")
	private String jwtSecret;

	@Value("${application.jwt.tokenExpirationAfterDays}")
	private int jwtExpirationMs;

	@Value("${application.jwt.tokenPrefix}")
	private String jwtCookie;

	public String getJwtFromCookies(HttpServletRequest request)
	{
		Cookie cookie = WebUtils.getCookie(request, jwtCookie);

		if(cookie != null) {
			return cookie.getValue();
		}
		else {
			return null;
		}

	}

	public ResponseCookie generateJwtCookie(UserDetailsImpl u)
	{
		String jwt = generateTokenFromUsername(u.getUsername(), u.getAuthorities());
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
											  .path("/api/v1/login")
											  .maxAge(24 * 60 * 60)
											  .httpOnly(true)
											  .build();
		return cookie;
	}
	
	public String generateTokenFromUsername(String userName, Collection<? extends GrantedAuthority> authorities)
	{
		SecretKey key = Keys.hmacShaKeyFor(JwtSecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

		//System.out.println("Here's the null authorities: " + populateAuthorities(authorities));
		
		return Jwts.builder()
					.setIssuer("Typology API")
					.setSubject("JWT Token")
					.claim("username", userName)
					.claim("authorities", populateAuthorities(authorities))
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + 30000000))		//expiration time of 8 hours
					.signWith(key)													//digitally sign by using secret key
					.compact();	
//		
//		return Jwts.builder()
//					.setSubject(username)
//					.setIssuedAt(new Date())
//					.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//					.signWith(key(), SignatureAlgorithm.HS256).compact();
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
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, null)
											  .path("/api/v1/login")
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
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
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

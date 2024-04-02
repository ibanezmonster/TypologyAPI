package com.typology.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.config.AppAuthenticationProvider;
import com.typology.config.SecurityConfig;
import com.typology.entity.user.AppUser;
import com.typology.jwt.JwtSecurityConstants;
import com.typology.jwt.JwtUtils;
import com.typology.service.AppUserService;
import com.typology.user.UserDetailsImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}")
public class LoginController
{
	
	@Autowired
	private AppAuthenticationProvider authentication;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	//@Autowired
	//private JWTTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUtils jwtUtils;

	private final Logger LOG = Logger.getLogger(SecurityConfig.class.getName());

	
	@Autowired
	private AppUserService appUserService; 
	
	
	//@Autowired
	//private AppUser appUserForAuthoritiesLookup;
	
	//@Order
	@PostMapping(path = "/login", 
				 consumes = {MediaType.APPLICATION_JSON_VALUE}, 
				 produces = {MediaType.APPLICATION_JSON_VALUE}) 
	public ResponseEntity<?> login(@Valid @RequestBody AppUser appUser){
		
		Authentication authObj;
		
		try {
			
			//set the user as an Authentication object in the SecurityContextHolder
			authObj = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getName(), appUser.getPwd()));
			
			SecurityContextHolder.getContext().setAuthentication(authObj);
			LOG.info("Placed in security context holder: " + SecurityContextHolder.getContext().getAuthentication().toString());				
			
			
			//look up AppUser in database in order to get authorities list, so that it can be stored in JWT
			AppUser appUserForAuthoritiesLookup = appUserService.getAppUserByName(appUser.getName());		//this is null for some reason
			LOG.info("Looking up: " + appUserForAuthoritiesLookup.getId() + ", authorities are: " + appUserForAuthoritiesLookup.getAuthorities().toString());
			
			UserDetailsImpl userDetails = UserDetailsImpl.build(appUserForAuthoritiesLookup);
			ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
		    
		    //response.setHeader(JwtSecurityConstants.JWT_HEADER, jwtCookie.toString());
		    
			return ResponseEntity.ok()
								 .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
								 .header(JwtSecurityConstants.JWT_HEADER, jwtCookie.toString())
								 .body("Successful login");
		}
		
		catch(BadCredentialsException ex) {
			return new ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED);
		}		
	}
}

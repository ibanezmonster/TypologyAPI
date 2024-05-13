package com.typology.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typology.config.AppAuthenticationProvider;
import com.typology.config.SecurityConfig;
import com.typology.dto.LoginDTO;
//import com.typology.dto.Mapper;
import com.typology.entity.user.AppUser;
import com.typology.security.JWT;
import com.typology.service.AppUserService;
import com.typology.user.UserDetailsImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}")
public class LoginController
{
	
	@Autowired
	private AppAuthenticationProvider authenticationProvider;

	@Autowired
	private JWT jwt;

	private final Logger LOG = Logger.getLogger(SecurityConfig.class.getName());

	
	@Autowired
	private ModelMapper modelMapper;
	
	
	//@Order
	@PostMapping(path = "/login", 
				 consumes = {MediaType.APPLICATION_JSON_VALUE}, 
				 produces = {MediaType.APPLICATION_JSON_VALUE}) 
	public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO){
		
		AppUser appUserLogin = new AppUser();
		
		try {
			appUserLogin = modelMapper.map(loginDTO, AppUser.class);		
		}
		
		catch(Exception e) {
			LOG.severe("Unable to convert LoginDTO to AppUser");
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			
			//set the user as an Authentication object in the SecurityContextHolder
			Authentication authObj = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(appUserLogin.getName(), appUserLogin.getPwd()));
						
			SecurityContextHolder.getContext().setAuthentication(authObj);  
			
			//look up AppUser in database in order to get authorities list, so that it can be stored in JWT
			LOG.info("Placed in security context holder: " + SecurityContextHolder.getContext().getAuthentication().toString());	
			LOG.info("For app user: " + authObj.getName() + ", authorities are: " + authObj.getAuthorities());

			//issue JWT
			AppUser appUserDetails = authenticationProvider.getAppUser();
			UserDetailsImpl userDetails = UserDetailsImpl.build(appUserDetails);
			ResponseCookie jwtCookie = jwt.generateJwtCookie(userDetails);		    		    
					    
			return ResponseEntity.ok()
								 .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
								 .header(HttpHeaders.AUTHORIZATION, jwtCookie.toString())
								 .body("Successful login");
		}
		
		
		catch(IllegalArgumentException ex) {
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
		
		catch(BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
								 .body(ex.getMessage()); 
					//ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED);
		}		
	}
}

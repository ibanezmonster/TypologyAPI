package com.typology.controller;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.typology.config.SecurityConfig;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Authority;
import com.typology.repository.AppUserRepository;
import com.typology.repository.AuthoritiesRepository;
import com.typology.security.AppUserRoles;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}")
public class RegistrationController {

	
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
	private final Logger LOG = Logger.getLogger(SecurityConfig.class.getName());


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody AppUser appUser) {
        AppUser savedCustomer = null;
        ResponseEntity<String> response = null;
        
        try {
        	//save user
        	String hashedPwd = passwordEncoder.encode(appUser.getPwd());
        	
        	appUser.setPwd(hashedPwd);
        	appUser.setRole(AppUserRoles.USER.toString());
        	appUser.setRegistrationTimestamp(ZonedDateTime.now());
        	appUser.setStatus("enabled");
            
        	savedCustomer = appUserRepository.save(appUser);
        	
        	//save authorities
        	//by default, give new user the 'VIEWTYPINGS' authority
        	Authority authority = new Authority();        	
        	authority.setCustomer(savedCustomer);
        	authority.setName("VIEWTYPINGS");
        	authoritiesRepository.save(authority);
        	
            if (savedCustomer.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED)
                        				 .body("Given user details are successfully registered");
            }
            
            
            //also log the authorities for the newly registered user
            //authorities list should not be null
//            System.out.println("Authorities list: " + appUser.getAuthorities());
//            
//            Set<GrantedAuthority> authorities = appUser.getAuthorities()
//													   .stream()
//													   .map(role -> new SimpleGrantedAuthority(role.getName()))
//													   .collect(Collectors.toSet());
//
//            LOG.info("Granted authorities for newly registered user: " + appUser.getName() + ": " + String.join(",", authorities.toString()));
        } 
        
        catch(DataIntegrityViolationException ex) {
        	response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    				 .body("This user name already exists.\nAn exception occured due to " + ex.getMessage());
        }
                
        
        catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        
        return response;
    }
}

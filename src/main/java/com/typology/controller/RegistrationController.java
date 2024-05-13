package com.typology.controller;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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
import com.typology.dto.RegistrationDTO;
import com.typology.entity.user.AppUser;
import com.typology.entity.user.Authority;
import com.typology.repository.AppUserRepository;
import com.typology.repository.AuthoritiesRepository;
import com.typology.security.AppUserRoles;
import com.typology.security.ValidPassword;

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
    
    @Autowired
	private ModelMapper modelMapper;
    
	private final Logger LOG = Logger.getLogger(SecurityConfig.class.getName());


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
    	
    	//@ValidPassword- invalid password will throw ApiException.handleMethodArgumentNotValidException
    	
        AppUser userToCreate;
        ResponseEntity<String> response = null;
        
        try {
        	userToCreate = modelMapper.map(registrationDTO, AppUser.class);
		}
		
		catch(Exception e) {
			LOG.severe("Unable to convert Registration to AppUser");
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
        try {
        	//check if user already exists        	
        	Optional<AppUser> possibleExistingUsername = appUserRepository.findByName(userToCreate.getName());        	        	
        	
        	if(!possibleExistingUsername.isEmpty()) {
        		return ResponseEntity.status(HttpStatus.CONFLICT)
       				 				 .body("This user name already exists");        		
        	}
        	
        	
        	//save user
        	String hashedPwd = passwordEncoder.encode(userToCreate.getPwd());
        	
        	userToCreate.setPwd(hashedPwd);
        	userToCreate.setRole(AppUserRoles.USER.toString());
        	userToCreate.setRegistrationTimestamp(ZonedDateTime.now());
        	userToCreate.setStatus("enabled");
            
        	userToCreate = appUserRepository.save(userToCreate);
        	
        	
        	
        	//save authorities
        	//by default, give new user the 'VIEWTYPINGS' authority
        	Authority authority = new Authority();        	
        	authority.setUser(userToCreate);
        	authority.setName("VIEWTYPINGS");
        	
        	authoritiesRepository.save(authority);
        	
        	
            if (userToCreate.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        			 .body("Given user details are successfully registered");
            }
            
            
            //log the authorities for the newly registered user         
            Set<GrantedAuthority> authorities = userToCreate.getAuthorities()
													        .stream()
													        .map(role -> new SimpleGrantedAuthority(role.getName()))
													        .collect(Collectors.toSet());

            LOG.info("Granted authorities for newly registered user: " + userToCreate.getName() + ": " + String.join(",", authorities.toString()));
        } 
        
        catch(DuplicateKeyException ex) {
        	response = ResponseEntity.status(HttpStatus.CONFLICT)
                    				 .body("This user name already exists");
        }
                
        
        catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        
        return response;
    }
}

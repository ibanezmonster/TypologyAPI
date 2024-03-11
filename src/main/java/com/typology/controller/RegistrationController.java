package com.typology.controller;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.typology.entity.user.AppUser;
import com.typology.repository.AppUserRepository;
import com.typology.security.AppUserRoles;

@RestController
public class RegistrationController {

	
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AppUser appUser) {
        AppUser savedCustomer = null;
        ResponseEntity<String> response = null;
        
        try {
        	String hashedPwd = passwordEncoder.encode(appUser.getPwd());
        	
        	appUser.setPwd(hashedPwd);
        	appUser.setRole(AppUserRoles.USER.toString());
        	appUser.setRegistrationTimestamp(ZonedDateTime.now());
        	appUser.setStatus("enabled");
            
        	savedCustomer = appUserRepository.save(appUser);
            
            if (savedCustomer.getId() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } 
        
        catch(DataIntegrityViolationException ex) {
        	response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("This user name already exists.\nAn exception occured due to " + ex.getMessage());
        }
                
        
        catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        
        return response;
    }

    
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
//        Customer savedCustomer = null;
//        ResponseEntity response = null;
//        try {
//            savedCustomer = customerRepository.save(customer);
//            if (savedCustomer.getId() > 0) {
//                response = ResponseEntity
//                        .status(HttpStatus.CREATED)
//                        .body("Given user details are successfully registered");
//            }
//        } catch (Exception ex) {
//            response = ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An exception occured due to " + ex.getMessage());
//        }
//        return response;
//    }

}

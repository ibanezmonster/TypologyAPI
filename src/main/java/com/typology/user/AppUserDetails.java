//package com.typology.user;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.typology.entity.user.AppUser;
//import com.typology.repository.AppUserRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class AppUserDetails implements UserDetailsService {
//
//    @Autowired
//    private AppUserRepository appUserRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        
//
//		try {
//			String userName = null, password = null;
//	        List<GrantedAuthority> authorities;
//	        AppUser customer;
//	        
//			customer = appUserRepository.findByName(userName)
//										.orElseThrow(Exception::new);
//
//	        userName = customer.getName();
//	        password = customer.getPwd();
//	        authorities = new ArrayList<>();
//	        authorities.add(new SimpleGrantedAuthority(customer.getRole()));
//	        
//
//	        return UserDetailsImpl.build(user);
//		}
//		catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//       
//    
//        
//    }
//
//}

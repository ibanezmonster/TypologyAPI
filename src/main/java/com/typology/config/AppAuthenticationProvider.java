package com.typology.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.typology.entity.user.AppUser;
import com.typology.entity.user.Authority;
import com.typology.repository.AppUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class AppAuthenticationProvider implements AuthenticationProvider
{

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private AppUser appUser;
	

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException, BadCredentialsException, IllegalArgumentException
	{

		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		appUser = appUserRepository.findByName(username)
								   .orElseThrow(IllegalArgumentException::new);
		
		if(appUser.getStatus().equals("disabled")) {
			throw new BadCredentialsException("Account is locked.");
		}
		
		List<GrantedAuthority> authorities = null;
		
		if(passwordEncoder.matches(pwd, appUser.getPwd())) {
			authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(appUser.getRole()));
		}

		else {
			throw new BadCredentialsException("Invalid password!");
		}
		
		return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
	}
	
	public AppUser getAppUser() {
		return appUser;
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
//	
//	private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities){
//		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//		
//		for(Authority authority:authorities) {
//			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
//		}
//		
//		return grantedAuthorities;
//	}

}

//
//@Component
//public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String pwd = authentication.getCredentials().toString();
//        List<Customer> customer = customerRepository.findByEmail(username);
//        
//        if (customer.size() > 0) {
//            if (passwordEncoder.matches(pwd, customer.get(0).getPwd())) {
//                List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
//                
//                return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
//            } 
//            
//            else {
//                throw new BadCredentialsException("Invalid password!");
//            }
//        }
//        
//        else {
//            throw new BadCredentialsException("No user registered with this details!");
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
//    }
//
//}

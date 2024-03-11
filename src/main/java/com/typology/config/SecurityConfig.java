package com.typology.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.typology.filter.JWTTokenGeneratorFilter;
import com.typology.filter.JWTTokenValidatorFilter;
import com.typology.filter.RequestValidationBeforeFilter;
import com.typology.filter.AuthoritiesLoggingAfterFilter;
import com.typology.filter.AuthoritiesLoggingAtFilter;
import com.typology.filter.CsrfCookieFilter;


import java.util.Arrays;
import java.util.Collections;


@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    	
    	CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
 	
    	//main
    	return http
    				//session management - comment this to be able to log in
    				//stateless says to not create jsessionids, i'll take care of everything myself
    				//this is supposed to be used for JWT only
    				//.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
    				.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) //login works
    				
    				//CORS
    				.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
    					
    					@Override
    					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
    						CorsConfiguration config = new CorsConfiguration();
    						config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
    						config.setAllowedMethods(Collections.singletonList("*"));
    						config.setAllowCredentials(true);
    						config.setAllowedHeaders(Collections.singletonList("*"));
    						config.setExposedHeaders(Arrays.asList("Authorization"));
    						config.setMaxAge(3600L);
							return config;
    					}    					
    				}))
    			
    				
    				//CSRF
    				.csrf(csrf -> csrf.disable())		//also remove CsrfCookieFilter and JWTTokenValidatorFilter
					//.csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
					//				  .ignoringRequestMatchers("/h2/**", "/register/**")
					//				  .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
					
					
					
					//Filters
					.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
					.addFilterAt(new AuthoritiesLoggingAtFilter(),BasicAuthenticationFilter.class)	                
					//.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)				//remove is csrf is disabled
	                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
	                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
	                //.addFilterAfter(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)		//remove is csrf is disabled			
				    
				    
				    //Authorization
				    .authorizeHttpRequests((authorizeHttpRequests) -> 	    					
					     				      authorizeHttpRequests.requestMatchers("/h2/**", "/register/**").permitAll()
						   								     //.requestMatchers("/profile/**").hasRole("user")
														     //.requestMatchers("/enneagram/**").hasRole("admin")
					     				      				 //.requestMatchers("/enneagram/**").authenticated() 	//test for method level security
														       .anyRequest().permitAll())
				    
				    //other
					.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) //this allows login to h2 console
					//.formLogin(Customizer.withDefaults())							//default login page					
					.formLogin(formLogin -> formLogin.loginProcessingUrl("/login")
													 .defaultSuccessUrl("/home")
													 .permitAll())													 
					//.httpBasic(Customizer.withDefaults())							//this will make registration HTTP only, REST registration will throw 401
					.build();
    }

    
    @Bean
    public UserDetailsManager userDetailsService() {
    	UserDetails admin = User.withUsername("a")
    							.password("123")
    							.authorities("admin")
    							.build();
    	
    	return new InMemoryUserDetailsManager(admin);
    }

    
    public UserDetailsService userDetailsService(AbstractDataSource dataSource) {		//example is just DataSource
    	return new JdbcUserDetailsManager(dataSource);
    }
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {    	
    	//return NoOpPasswordEncoder.getInstance(); //only used for non-prod
        return new BCryptPasswordEncoder();
    }
}























//return http.authorizeHttpRequests((authorizeHttpRequests) -> 	    					
//					authorizeHttpRequests.requestMatchers("/h2/**", "/register/**").permitAll()
//										 //.requestMatchers("/profile/**").hasRole("user")
//										 //.requestMatchers("/enneagram/**").hasRole("admin")
//    									 .anyRequest().authenticated())	
//					.csrf(csrf -> csrf.ignoringRequestMatchers("/h2/**", "/register/**"))
//					.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) //this allows login to h2 console
//					.formLogin(Customizer.withDefaults())							//without this, login page is blocked
//					//.httpBasic(Customizer.withDefaults())							//this will make registration HTTP only, REST registration will throw 401
//					.build();
 
//allowing just h2 and login
//return http
//        .authorizeHttpRequests()
//        .requestMatchers("/h2/**", "/register/**").permitAll()
//        .anyRequest().authenticated()
//        .and()
//        .formLogin()
//        .and()
//        .csrf().ignoringRequestMatchers("/h2/**", "/register/**")
//        .and()
//        .headers().frameOptions().sameOrigin()		//this allows login to h2 console
//        .and()
//        .build();


	//.authorizeRequests()
    //.requestMatchers("/h2-console/**").permitAll());
			   //.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.anyRequest()
	 			//																	  .permitAll()));

//http.requestMatchers("/h2-console/**") // Restrict security configuration to H2 console paths
//    .authorizeRequests()
//    .anyRequest().permitAll()
//    .and()
//    .headers().frameOptions().sameOrigin() // Enable frame options for H2 console
//    .and()
//    .csrf().disable(); // Disable CSRF for H2 console



//CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//requestHandler.setCsrfRequestAttributeName("_csrf");
//
//http.securityContext((context) -> context.requireExplicitSave(false))
//        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//        .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//    @Override
//    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//        config.setAllowedMethods(Collections.singletonList("*"));		//GET, POST, etc. "*" for all
//        config.setAllowCredentials(true);								//allow passing of credentials to and from this application
//        config.setAllowedHeaders(Collections.singletonList("*"));		//allow all types of headers
//        config.setMaxAge(3600L);										//3600 seconds = cache one hour, normally 1 day/7 days/etc.
//        return config;
//    }
//        }))
//        .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact","/register")
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))				//withHttpOnlyFalse- allows Angular to read cookie value
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)			//please execute the csrfcookiefilter after the basic authentication filter
//                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class) //will show RequestValidationBeforeFilter right before BasicAuthenticationFilter
//                 .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class) //at = randomly before or after, not used much but might be used when something is in progress 
//                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
//        .authorizeHttpRequests((requests)->requests
//                .requestMatchers("/myAccount").hasRole("USER")
//                .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
//                .requestMatchers("/myLoans").hasRole("USER")
//                .requestMatchers("/myCards").hasRole("USER")
//                .requestMatchers("/user").authenticated()		//any logged in user can access this
//                .requestMatchers("/notices","/contact","/register").permitAll())
//        .formLogin(Customizer.withDefaults())
//        .httpBasic(Customizer.withDefaults());


//return http.build();
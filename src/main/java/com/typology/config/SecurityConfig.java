package com.typology.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

import com.typology.filter.JWTTokenValidatorFilter;
import com.typology.filter.RequestValidationBeforeFilter;
import com.typology.security.AppUserAuthorities;
import com.typology.security.AppUserRoles;
import com.typology.filter.AuthoritiesLoggingAfterFilter;
import com.typology.filter.CsrfCookieFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	 private final Logger LOG = Logger.getLogger(SecurityConfig.class.getName());

	 @Bean
	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	  }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    	
    	
    	//csrf
    	//CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        //requestHandler.setCsrfRequestAttributeName("_csrf");
 	
    	//main
    	return http
    				//normally true, by overriding to false we are giving the repsonsibility of generating the JsessionId 
    				//and storing authentication details into the security context folder to the framework. 
    				//Needed if using separate UI app
    				//.securityContext((context) -> context.requireExplicitSave(false)) 
    				
    			
    				//session management    				
    						//for JWT- does not create an HttpSession or jsessionids, need to use JWT to take care of everything myself
    				.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    				
    						//use for if using JSessionID and CSRF token
    				//.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) 
    				
    				
    				//If our stateless API uses token-based authentication, such as JWT, we don’t need CSRF protection, and we must disable it as we saw earlier.
    				//However, if our stateless API uses a session cookie authentication, we need to enable CSRF protection as we’ll see next.
    				
    				//CORS
    				.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
    					
    					@Override
    					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
    						CorsConfiguration config = new CorsConfiguration();
    						//config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
    						config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:1334"));  //allow SQL Server on port 1334
    						config.setAllowedMethods(Collections.singletonList("*"));
    						config.setAllowCredentials(true);
    						config.setAllowedHeaders(Collections.singletonList("*"));	
    						config.setExposedHeaders(Arrays.asList("Authorization"));	//passing header name to send as a response to UI app
    						config.setMaxAge(3600L);
							return config;
    					}    					
    				}))
    			
    				
    				//CSRF- not needed if stateless, using JWT
    				.csrf(csrf -> csrf.disable()	//also remove CsrfCookieFilter and JWTTokenValidatorFilter
					//.csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)		//ensure 
					//				  .ignoringRequestMatchers("/h2/**", "/api/" + EnvironmentProperties.getApiVersion() + "/register/**", 
    				//													"/api/" + EnvironmentProperties.getApiVersion() + "/login")
									  
									  
									  
									  //CookieCsrfTokenRepository is intended to be used only when the client application is developed in a framework such as Angular.
									  //It follows AngularJS conventions and stores the CsrfToken object in a cookie named XSRF-TOKEN and in the header X-XSRF-TOKEN.
									  //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
							)
					
					//XSS
//					.headers(headers ->headers.xssProtection(
//						                        xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
//											  .contentSecurityPolicy(
//						                        cps -> cps.policyDirectives("script-src 'self'")
//	                ))
					
					
					//Filters
								//login only
					//.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
					
								//JWT
					.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
					
								//logging					
					//.addFilterAt(new AuthoritiesLoggingAtFilter(),BasicAuthenticationFilter.class)	           
		            .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)	                
	                
	                
		            			//non-JWT: if using csrf
					//.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
		            //.addFilterBefore(new UsernameAndPasswordAuthenticationFilter(), BasicAuthenticationFilter.class)		//or .addFilterBefore(new JwtUsernameAndPasswordAuthenticationFilter(), BasicAuthenticationFilter.class)
					//.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)				//remove if csrf is disabled
	               
				    
				    
				    //Authorization
				    .authorizeHttpRequests((authorizeHttpRequests) -> 	    					
					     				      authorizeHttpRequests.requestMatchers("/api/" + EnvironmentProperties.getApiVersion() + "/register", 
					     				    		  								"/api/" + EnvironmentProperties.getApiVersion() + "/login")
					     				      										.permitAll()
					     				      										
					     				      					   //admin console- note: cannot use 'admin' in path name, it won't work
					     				      					   .requestMatchers("/console/**")
					     				      					   					.hasRole(AppUserRoles.ADMIN.toString())	

					     				      					   //typings privileges
					     				      					   .requestMatchers("/api/" + EnvironmentProperties.getApiVersion() + "/my_typings",
								     				      							"/api/" + EnvironmentProperties.getApiVersion() + "/profile/{entryName}/vote/{typologySystem}",
					     				      							   			"/api/" + EnvironmentProperties.getApiVersion() + "/profile/{entryName}/vote/{typologySystem}",
					     				      							   			"/api/" + EnvironmentProperties.getApiVersion() + "/profile/{entryName}/my_typing/{typologySystem}")
					     				      					   					.hasAuthority(AppUserAuthorities.ADDTYPINGS.toString())
					     				      					   					     				      							   			
					     				      					   //h2
					     				      				  	   //.requestMatchers("/h2/**").permitAll()
					     				      					   		
					     				      					   //everything that is not register or login is authenticated
					     				      				  	   .anyRequest().authenticated()
					     				      				 )
				    
				    //other
					.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) //this allows login to h2 console
				    //.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())	//if needed
					//.formLogin(Customizer.withDefaults())							//default login page					
					.formLogin(formLogin -> formLogin.loginProcessingUrl("/login")
													 .defaultSuccessUrl("/home")
													 .permitAll()
													 .successHandler(new AuthenticationSuccessHandler() {
															 @Override
															 public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
																	 throws IOException, ServletException{
																	System.out.println("user name: " + authentication.getName());
																	
																	UrlPathHelper helper = new UrlPathHelper();
																	String contextPath = helper.getContextPath(request);
																	
																	response.sendRedirect(contextPath);
															}})
													 
													.failureHandler(new AuthenticationFailureHandler() {
															@Override
															public void onAuthenticationFailure(HttpServletRequest request,
																	HttpServletResponse response,
																	AuthenticationException exception)
																	throws IOException, ServletException
															{
																System.out.println("Exception: " + exception.getMessage());
																
																UrlPathHelper helper = new UrlPathHelper();
																String contextPath = helper.getContextPath(request);
																
																response.sendRedirect(contextPath);														
															}})
					)
					
					//logout not necessary for API
					//but for UI, can just remove token from browser
					
					//consider access token and refresh token solution
					//can blacklist refresh token
					
					//look into jwks
					
//					.logout(logout -> logout.permitAll()
//											.logoutUrl("/logout")
//											.logoutSuccessUrl("/logout_success")
//											.logoutSuccessHandler(new LogoutSuccessHandler() {
//												@Override
//												public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
//													LOG.info("Logout success for: " + authentication.getPrincipal());
//													response.sendRedirect("/logout_success");
//												}
//											}))	
			
					//OAUTH2
					//.oauth2Login(login -> login.loginPage("/login")	
					//							.userInfoEndpoint(userService -> userService(oauth2UserService)))
					
					
					//logout
					//.logout(l -> logout.permitAll())
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
    
    
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
////                .withUser("user").password("{noop}password").roles("USER")
////            .and()
//                .withUser("admin").roles("ADMIN");
//    }
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
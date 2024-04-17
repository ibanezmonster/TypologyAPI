package com.typology.filter;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.typology.config.SecurityConfig;
import com.typology.dto.LoginDTO;

import jakarta.validation.Valid;

@Component
@Aspect
@EnableAspectJAutoProxy
public class LoggerAOP
{
	private final Logger LOG = Logger.getLogger(LoggerAOP.class.getName());

	
	//Registration-------------------
	@Pointcut("execution(* com.typology.controller.RegistrationController.registerUser(..))")
	public void userSuccessfulRegistration() {
		LOG.info("User registration successful");
	}
	
	@AfterReturning(pointcut = "userSuccessfulRegistration()",
					returning = "re")
	public void registrationStatusCode(ResponseEntity<?> re) {
		LOG.info(re.getStatusCode().toString());
	}
	
	
	//Login-------------------
	@After("execution(* com.typology.controller.LoginController.login(..))")
	public void userSuccessfulLogin() {
		LOG.info("User login successful");
	}
	
	
	@Before("execution(* com.typology.controller.RegistrationController.registerUser(..))")
	public void testingBeforeRegistration(JoinPoint jp) {
		LOG.info("Attempting new user registration for user: " + jp.getArgs()[0].toString());
	}
	
//	
//	@Pointcut("within()")
//	public void something() {
//		LOG.info("fdsa");
//	}
	
	
	
}

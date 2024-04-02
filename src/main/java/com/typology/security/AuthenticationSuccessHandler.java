package com.typology.security;

import java.io.IOException;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationSuccessHandler
{

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException //throws IOException, ServletException
	{
		// TODO Auto-generated method stub
		
	}

}

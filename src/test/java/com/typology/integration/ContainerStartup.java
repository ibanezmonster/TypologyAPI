package com.typology.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;


//https://medium.com/@apusingh1967/how-to-use-mssql-server-as-test-container-for-integration-testing-using-junit-and-spring-boot-e5e299e1bd97
public abstract class ContainerStartup
{
	
	static DockerImageName myImage = DockerImageName.parse("mcr.microsoft.com/mssql/server:2019-latest")
											 		.asCompatibleSubstituteFor("mcr.microsoft.com/mssql/server");
	
	static final String containerName = "Typology-SQL-Server-Test-Container";
	static final int sqlServerPort = 1433;
	static final String pwd = "Iba594#spsx@nez!";
	static final String db = "test_typology_db";
	
	@Container
	private static final MSSQLServerContainer<?> SQLSERVER_CONTAINER = 
											new MSSQLServerContainer<>(myImage)
												.withCreateContainerCmdModifier(cmd -> 
													cmd.withName(containerName))
												.withExposedPorts(sqlServerPort)
												//.withDatabaseName(db)
												.withPassword(pwd)																			
												//.withUsername("sa")
												//.withPassword("Iba594#spsx@nez!")
												.acceptLicense();
	
	static {
		  //SQLSERVER_CONTAINER.setPortBindings(Arrays.asList("57256"));	//run on port 57256 also
		  
		  //SQLSERVER_CONTAINER.withUsername("sa");	//cannot set username- throws ExceptionInInitializerError, use "sa" anyways
		  //SQLSERVER_CONTAINER.withPassword("Iba594#spsx@nez!");
		  SQLSERVER_CONTAINER.start();
		  SQLSERVER_CONTAINER.getContainerId();
		  //SQLSERVER_CONTAINER.setDockerImageName("Typology SQL Server Test Container");
		  //System.out.println(SQLSERVER_CONTAINER.getDatabaseName());
		  System.out.println(SQLSERVER_CONTAINER.getUsername());
		  System.out.println(SQLSERVER_CONTAINER.getPassword());
		  System.out.println(SQLSERVER_CONTAINER.getJdbcUrl());
		 }
	
	@DynamicPropertySource
	public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", SQLSERVER_CONTAINER::getJdbcUrl);
		registry.add("spring.datasource.username", SQLSERVER_CONTAINER::getUsername);
		registry.add("spring.datasource.password", SQLSERVER_CONTAINER::getPassword);
	}
	
	
//	@After
//	public void after(){
//	  database.close();
//	}
}

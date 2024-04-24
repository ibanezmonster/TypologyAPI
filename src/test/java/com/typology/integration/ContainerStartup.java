package com.typology.integration;

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
	
	@Container
	private static final MSSQLServerContainer<?> SQLSERVER_CONTAINER = new MSSQLServerContainer<>(myImage)
	 																		//.setImage("MSSQLImage")
	 																		.acceptLicense();
	
	static {
		  SQLSERVER_CONTAINER.start();
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
}

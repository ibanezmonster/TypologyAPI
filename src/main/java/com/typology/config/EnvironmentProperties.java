package com.typology.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({"classpath:appInfo.properties",
				"classpath:actuator.properties",
				"classpath:db.properties",
				"classpath:hibernate.properties",
				"classpath:security.properties"})	
public class EnvironmentProperties implements EnvironmentAware {

	@Autowired
    private static Environment environment;

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Lazy
    public static String getApiVersion() {    	
        return environment.getProperty("api.version");
    }
    
    @Bean
    @Lazy
    public static String getJWTSecretKey() {    	
        return environment.getProperty("application.jwt.secretKey");
    }
    
    @Bean
    @Lazy
    public static String getJWTExpirationAfterDays() {    	
        return environment.getProperty("application.jwt.tokenExpirationAfterDays");
    }
    
    @Bean
    @Lazy
    public static String getJWTTokenPrefix() {    	
        return environment.getProperty("application.jwt.tokenPrefix");
    }
    
//    @Bean
//    @Lazy
//    public static String getJWTSecretKey() {    	
//        return environment.getProperty("application.jwt.secretKey");
//    }
}
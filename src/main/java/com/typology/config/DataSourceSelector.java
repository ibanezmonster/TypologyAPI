//package com.typology.config;
//
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import javax.sql.DataSource;
//
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.EventListener;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.init.DataSourceInitializer;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//import org.springframework.stereotype.Component;
//
//
//
//@Configuration
////@EnableJpaRepositories(basePackages = {"com.typology.repository",
////									   "com.typology.repository"
////									  })
//public class DataSourceSelector
//{
//	@Autowired
//    private DataSource dataSource;
//	
//	
////	@Before
////    public void beforeTest() throws SQLException {
////
////        Connection connection = DriverManager.getConnection(
////                "jdbc:h2:mem:PROJECT;MVCC=true;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:init-h2.sql';MODE=Oracle;DB_CLOSE_ON_EXIT=FALSE", "sa", "");
////
////        Reader data = new StringReader("small.sql");
////
////        RunScript.execute(connection, data);
////    }
////	
//	
//	
//	@EventListener(ApplicationReadyEvent.class)
//    public void loadData() {
//		
//        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("data_main.sql"));
//        resourceDatabasePopulator.execute(dataSource);
//    }
//	
//	
////	@Bean
////    public DataSourceInitializer dataSourceInitializerMain(@Qualifier("datasourcemain") DataSource datasource) {
////        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
////        resourceDatabasePopulator.addScript(new ClassPathResource("/data_main.sql"));
////
////        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
////        dataSourceInitializer.setDataSource(datasource);
////        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
////        return dataSourceInitializer;
////    }
//
////	@Bean
////	public DataSourceInitializer dataSourceInitializer2(@Qualifier("datasource2") DataSource datasource) {
////	    ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
////        resourceDatabasePopulator.addScript(new ClassPathResource("data2.sql"));
////
////	
////        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
////        dataSourceInitializer.setDataSource(datasource);
////        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
////        return dataSourceInitializer;
////	}
//}

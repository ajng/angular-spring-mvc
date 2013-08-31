package com.splendidcode.angular.sample.startup;

import com.googlecode.flyway.core.Flyway;
import org.h2.Driver;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.PROXY)
public class TestDatabaseConfig {

   @Bean
   NamedParameterJdbcTemplate jdbcTemplate() {
      return new NamedParameterJdbcTemplate(dataSource());
   }

   @Bean
   DataSource dataSource() {
      SimpleDriverDataSource dataSource = new SimpleDriverDataSource(new Driver(), "jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1", "sa", "sa");
      Flyway flyway = new Flyway();
      flyway.setDataSource(dataSource);
      flyway.setLocations("com/splendidcode/angular/sample/db");
      flyway.migrate();
      return dataSource;
   }
   
   @Bean
   PlatformTransactionManager txManager(){
      return new DataSourceTransactionManager(dataSource());
   }
   

}

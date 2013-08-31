package com.splendidcode.angular.sample.startup;

import com.googlecode.flyway.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.PROXY)
public class TestDatabaseConfig {

   @Bean
   NamedParameterJdbcTemplate jdbcTempalte() {
      return new NamedParameterJdbcTemplate(dataSource());
   }

   @Bean
   DataSource dataSource() {
      JdbcConnectionPool dataSource = JdbcConnectionPool.create("jdbc:he:mem:", "sa", "sa");
      Flyway flyway = new Flyway();
      flyway.setDataSource(dataSource);
      flyway.setLocations("com/splendidcode/angular/sample/db");
      flyway.migrate();
      return dataSource;
   }

}

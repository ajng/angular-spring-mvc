package com.splendidcode.angular.sample.startup;

import com.googlecode.flyway.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.PROXY)
public class DatabaseConfig {

   @Value("${db.password}")
   private String dbPassword;
   @Value("${db.url}")
   private String dbUrl;
   @Value("${db.user}")
   private String dbUser;

   @Value("${db.tcp_port:}")
   private String tcpPort;


   @Bean(destroyMethod = "stop")
   Server h2TcpServer() throws SQLException {
      if(!StringUtils.isEmpty(tcpPort)) {
         Server bean = Server.createTcpServer("-tcpAllowOthers", "-tcpPort", tcpPort);
         bean.start();
         return bean;
      }
      return null;
   }

   @Bean
   NamedParameterJdbcTemplate jdbcTempalte() {
      return new NamedParameterJdbcTemplate(dataSource());
   }

   @Bean
   DataSource dataSource() {
      JdbcConnectionPool dataSource = JdbcConnectionPool.create(dbUrl, dbUser, dbPassword);
      Flyway flyway = new Flyway();
      flyway.setDataSource(dataSource);
      flyway.setLocations("com/splendidcode/angular/sample/db");
      flyway.migrate();
      return dataSource;
   }

}

package com.splendidcode.angular.sample.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;

import static org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(basePackages = "com.splendidcode.angular.sample", excludeFilters = {@Filter(Controller.class), @Filter(Configuration.class)})
@Import({DevConfig.class, ProductionConfig.class, SecurityConfig.class, DatabaseConfig.class })
public class SpringConfig {
   
   @Bean
   static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
      PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
      bean.setIgnoreUnresolvablePlaceholders(false);
      bean.setIgnoreResourceNotFound(true);
      return bean;
   }

   
}



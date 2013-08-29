package com.splendidcode.angular.sample.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static org.springframework.context.annotation.ComponentScan.*;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.splendidcode.angular.sample", useDefaultFilters = false, includeFilters = {@Filter(Controller.class)})
public class MvcConfig extends WebMvcConfigurerAdapter{

    @Bean
    static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
        bean.setIgnoreUnresolvablePlaceholders(false);
        bean.setIgnoreResourceNotFound(true);
        return bean;
    }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      super.addResourceHandlers(registry);
      registry.addResourceHandler("/ui/**").addResourceLocations("/ui/");
   }
}

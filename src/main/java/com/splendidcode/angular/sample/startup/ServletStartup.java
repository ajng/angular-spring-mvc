package com.splendidcode.angular.sample.startup;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.Filter;
import java.io.IOException;

@SuppressWarnings("UnusedDeclaration")
public class ServletStartup extends AbstractDispatcherServletInitializer implements WebApplicationInitializer {
   private AnnotationConfigWebApplicationContext rootContext;

   @Override
   protected String[] getServletMappings() {
      return new String[]{"/*"};
   }

   @Override
   protected WebApplicationContext createRootApplicationContext() {
      AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
      addApplicationProperties(context);
      context.register(SpringConfig.class);
      rootContext = context;
      return context;
   }

   @Override
   protected WebApplicationContext createServletApplicationContext() {
      AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
      addApplicationProperties(context);
      context.register(MvcConfig.class);
      return context;
   }


   @Override
   protected Filter[] getServletFilters() {
      return new Filter[]{new DelegatingFilterProxy("shiroFilter", rootContext)};
   }

   //Add application.properties before registering any configuration so that we can hard-code
   //the active profile if desired
   private void addApplicationProperties(ConfigurableApplicationContext context) throws RuntimeException {
      try {
         ConfigurableEnvironment environment = context.getEnvironment();
         environment.getPropertySources().addLast(new ResourcePropertySource("classpath:com/splendidcode/angular/sample/application.properties"));
         System.out.println(environment.getDefaultProfiles());
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}

package com.splendidcode.angular.sample.startup;


import com.splendidcode.angular.sample.login.AuthenticationController;
import com.splendidcode.angular.sample.login.Shiro.Pbkdf2AuthenticationRealm;
import com.splendidcode.angular.sample.login.Shiro.SinglePageApplicationAuthFilter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AbstractAuthenticator;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {

   @Bean
   static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
      return new LifecycleBeanPostProcessor();
   }

   @Inject
   Pbkdf2AuthenticationRealm pbkdf2AuthenticationRealm;

   @Inject
   Collection<AuthenticationListener> authenticationListeners;

   @Bean
   org.apache.shiro.mgt.SecurityManager securityManager() {
      DefaultWebSecurityManager bean = new DefaultWebSecurityManager();
      bean.setRealm(pbkdf2AuthenticationRealm);
      AbstractAuthenticator authenticator = (AbstractAuthenticator)bean.getAuthenticator();
      authenticator.setAuthenticationListeners(authenticationListeners);
      return bean;
   }


   @Bean
   Object shiroFilter() {
      ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
      factory.setLoginUrl(AuthenticationController.LOGIN_URL);
      factory.setSecurityManager(securityManager());

      final String spaAuthFilterName = "spaAuthFilter";

      Map<String, String> filterDefinitions = new LinkedHashMap<String, String>() {{
         put("/", "anon");
         put("/login/add-user", "anon");
         put("/ui/**", "anon");
         put("/**", spaAuthFilterName);
      }};
      factory.setFilterChainDefinitionMap(filterDefinitions);
      factory.postProcessBeforeInitialization(new SinglePageApplicationAuthFilter(), spaAuthFilterName);
      return factory;
   }

   @Bean
   @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
   Subject currentUser() {
      return SecurityUtils.getSubject();
   }


}

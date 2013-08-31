package com.splendidcode.angular.sample.startup;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TestDatabaseConfig.class})
public class SpringTestConfig {
   
   
}

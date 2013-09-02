package com.splendidcode.angular.sample.startup;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("dev")
@PropertySource({"classpath:com/splendidcode/angular/sample/development.properties"})
public class DevConfig {
}
package com.splendidcode.angular.sample.startup;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("production")
@PropertySource({"classpath:com/splendidcode/angular/sample/production-defaults.properties","${sample.external_config_url:}"})
public class ProductionConfig {
}

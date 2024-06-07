package com.wileyedge.melodymap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration

//@ComponentScan is configured to scan the base packages containing your DAOs and models.
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = CommandLineRunner.class))

//@EnableAutoConfiguration allows Spring Boot to auto-configure the necessary components.
@EnableAutoConfiguration
public class TestApplicationConfiguration {

}

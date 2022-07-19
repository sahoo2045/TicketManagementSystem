package com.maxxton.ticketmanagent.ticketmanagement_parent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaRepositories
@SpringBootApplication
@EnableAutoConfiguration
@EnableSwagger2
public class TicketManagentApplication {
	public static void main(String[] args) {
		SpringApplication.run(TicketManagentApplication.class, args);
	}
	
	@Bean
	public Docket api() {
	   return new Docket(DocumentationType.SWAGGER_2)
	     .select()
	     .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
	     .build();
	}

}

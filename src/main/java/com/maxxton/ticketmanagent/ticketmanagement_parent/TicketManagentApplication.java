package com.maxxton.ticketmanagent.ticketmanagement_parent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class TicketManagentApplication {
		public static void main(String[] args) {
			SpringApplication.run(TicketManagentApplication.class, args);
		}

	}

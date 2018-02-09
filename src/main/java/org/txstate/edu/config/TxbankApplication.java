package org.txstate.edu.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("org.txstate.edu")
@EnableJpaRepositories({"org.txstate.edu.repository"})
@EntityScan("org.txstate.edu.model")
public class TxbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxbankApplication.class, args);
	}
}

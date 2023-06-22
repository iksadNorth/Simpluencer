package com.iksad.simpluencer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SimpluencerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpluencerApplication.class, args);
	}

}

package org.monarchinitiative;

import org.monarchinitiative.exomiser.autoconfigure.EnableExomiser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableExomiser
@SpringBootApplication
public class ExomiserDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExomiserDemoApplication.class, args);
	}

}

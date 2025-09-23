package com.marensovich.eljur;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Eljur application.
 */
@SpringBootApplication
public class EljurApplication {



	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.directory("./") // path to .env file
				.ignoreIfMissing()
				.load();

		// load system env variables
		dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));

		SpringApplication.run(EljurApplication.class, args);
	}

}

package by.aleksabrakor.libraryProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // включение планировщика
public class LibrarySpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarySpringBootApplication.class, args);
	}

}

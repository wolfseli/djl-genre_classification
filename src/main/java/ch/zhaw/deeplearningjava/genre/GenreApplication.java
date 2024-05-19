// Einstiegspunkt für Spring Boot-Anwendung, definiert Hauptklasse der Anwendung, die für Starten der Spring Boot-Anwendung verantwortlich ist. 

package ch.zhaw.deeplearningjava.genre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenreApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenreApplication.class, args);
	}

}


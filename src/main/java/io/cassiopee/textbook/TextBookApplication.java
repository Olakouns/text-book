package io.cassiopee.textbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


@SpringBootApplication
@EntityScan(basePackageClasses = {
		TextBookApplication.class,
		Jsr310JpaConverters.class
})
public class TextBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextBookApplication.class, args);
	}

}

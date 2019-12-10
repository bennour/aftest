package fr.salah.aftest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
@EnableSwagger2
public class AfTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfTestApplication.class, args);
	}

}

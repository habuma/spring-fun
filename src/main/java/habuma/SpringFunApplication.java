package habuma;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringFunApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFunApplication.class, args);
	}
	
	@Bean
	public ApplicationRunner dataLoader(BookRepository repo) {
		return args -> {
			repo.save(new Book(null, "1111122222", "Knitting with Dog Hair", "Kendall Crolius"));
		};
	}

}

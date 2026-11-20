package cat.wizard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Cormorant {

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

    @RequestMapping("/actuator/health")
    public String health() {
        return "UP";
    }

	public static void main(String[] args) {
		SpringApplication.run(Cormorant.class, args);
	}

}
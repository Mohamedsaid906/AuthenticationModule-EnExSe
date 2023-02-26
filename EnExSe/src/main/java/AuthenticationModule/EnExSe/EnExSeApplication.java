package AuthenticationModule.EnExSe;

import AuthenticationModule.EnExSe.Entities.Role;
import AuthenticationModule.EnExSe.Entities.User;
import AuthenticationModule.EnExSe.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class EnExSeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnExSeApplication.class, args);

	}
	@Bean
	BCryptPasswordEncoder getBCE() {
		return new BCryptPasswordEncoder();
	}
}

package br.com.fiap.gs.GlobalSolution;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Energy Transfer - Global Solution API",
		version = "1",
		description = "API desenvolvida para a Global Solution.",
		contact = @Contact(
				name = "Energy Transfer",
				email = "rm554012@fiap.com.br"
		)
))
public class GlobalSolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalSolutionApplication.class, args);
	}

}

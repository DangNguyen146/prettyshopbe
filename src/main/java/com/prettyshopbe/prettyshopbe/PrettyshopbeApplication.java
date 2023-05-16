package com.prettyshopbe.prettyshopbe;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@SpringBootApplication
public class PrettyshopbeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrettyshopbeApplication.class, args);
	}

}

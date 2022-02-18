package com.lineate.presentationvotingservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Presentation Voting Service API",
        version = "1.0", description = "Presentation Voting Service API"))
public class PresentationVotingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PresentationVotingServiceApplication.class, args);
    }
}
package com.example.presentationvotingservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ClientDtoResponse", description = "Client Info")
public class ClientResponse {

    long id;

    @Schema(name = "firstName", description = "Client firstName", required = true, example = "John")
    private String firstName;

    @Schema(name = "lastName", description = "Client lastName", required = true, example = "Doe")
    private String lastName;

    @Schema(name = "login", description = "Client login", required = true, example = "User's E-mail recommended")
    private String login;
}
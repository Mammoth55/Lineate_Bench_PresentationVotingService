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
@Schema(name = "SingerDtoResponse", description = "Singer Info")
public class UserResponse {

    long id;

    @Schema(name = "firstName", description = "User firstName", required = true, example = "John")
    private String firstName;

    @Schema(name = "lastName", description = "User lastName", required = true, example = "Doe")
    private String lastName;

    @Schema(name = "login", description = "User login", required = true, example = "User's E-mail recommended")
    private String login;

    @Schema(name = "password", description = "User password", required = true, example = "User's secret word")
    private String password;
}
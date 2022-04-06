package com.lineate.presentationvotingservice.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String login;
    private String password;
}
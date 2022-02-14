package com.example.presentationvotingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresentationRequest {

    private String name;

    private String description;

    private String creationTime;

    private String startTime;

    private String authorLogin;
}
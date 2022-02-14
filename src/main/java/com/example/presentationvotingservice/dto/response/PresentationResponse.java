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
@Schema(name = "PresentationDtoResponse", description = "Presentation Info")
public class PresentationResponse {

    long id;

    @Schema(name = "name", description = "Presentation name", required = true, example = "Java")
    private String name;

    @Schema(name = "description", description = "Presentation description", required = true, example = "Java in action")
    private String description;

    @Schema(name = "creationTime", description = "Presentation creationTime", required = true, example = "2022-01-31")
    private String creationTime;

    @Schema(name = "startTime", description = "Presentation startTime", required = true, example = "2022-05-31")
    private String startTime;

    @Schema(name = "authorLogin", description = "Presentation author", required = true, example = "xx@xxx.com")
    private String authorLogin;
}
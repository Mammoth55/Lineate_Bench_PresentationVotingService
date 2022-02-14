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
@Schema(name = "VoteDtoResponse", description = "Vote Info")
public class VoteResponse {

    private Long id;

    @Schema(name = "voteStatus", description = "Vote Status", required = true, example = "WANT_LOOK")
    private String voteStatus;

    @Schema(name = "voteTime", description = "Vote Time", required = true, example = "2022-01-31 14:05:55")
    private String voteTime;

    @Schema(name = "clientLogin", description = "Client Login", required = true, example = "xx@xxx.com")
    private String clientLogin;

    @Schema(name = "presentationName", description = "Presentation name", required = true, example = "Java in Action")
    private String presentationName;
}
package com.lineate.presentationvotingservice.controller;

import com.lineate.pojolib.dto.request.VoteRequest;
import com.lineate.pojolib.dto.response.VoteResponse;
import com.lineate.presentationvotingservice.service.VoteService;
import com.lineate.presentationvotingservice.entity.Vote;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private static final String ID = "id";
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/")
    public ResponseEntity<List<VoteResponse>> getAll() {
        return ResponseEntity.ok().body(voteService.getAll().stream()
                .map(this::convertVoteToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<VoteResponse> getById(@PathVariable(ID) long id) {
        return ResponseEntity.ok().body(convertVoteToDto(voteService.getById(id)));
    }

    @PostMapping("/")
    public ResponseEntity<VoteResponse> create(@RequestBody VoteRequest voteRequest) {
        return ResponseEntity.ok().body(convertVoteToDto(voteService.create(voteRequest)));
    }

    private VoteResponse convertVoteToDto(Vote vote) {
        return VoteResponse.builder()
                .id(vote.getId())
                .voteStatus(vote.getVoteStatus().name())
                .voteTime(vote.getVoteTime().toString())
                .clientLogin(vote.getClient().getLogin())
                .presentationName(vote.getPresentation().getName())
                .build();
    }
}
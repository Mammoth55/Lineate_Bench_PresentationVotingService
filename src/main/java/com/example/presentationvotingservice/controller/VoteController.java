package com.example.presentationvotingservice.controller;

import com.example.presentationvotingservice.dto.request.VoteRequest;
import com.example.presentationvotingservice.dto.response.VoteResponse;
import com.example.presentationvotingservice.entity.Vote;
import com.example.presentationvotingservice.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
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
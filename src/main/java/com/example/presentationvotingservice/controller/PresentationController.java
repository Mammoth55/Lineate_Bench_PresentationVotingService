package com.example.presentationvotingservice.controller;

import com.example.presentationvotingservice.dto.response.PresentationResponse;
import com.example.presentationvotingservice.dto.response.UserResponse;
import com.example.presentationvotingservice.dto.request.PresentationRequest;
import com.example.presentationvotingservice.model.Presentation;
import com.example.presentationvotingservice.service.PresentationService;
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
@RequestMapping("/api/presentations")
public class PresentationController {

    private static final String ID = "id";
    private final PresentationService presentationService;

    public PresentationController(PresentationService presentationService) {
        this.presentationService = presentationService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PresentationResponse>> getAll() {
        return ResponseEntity.ok().body(presentationService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<PresentationResponse> getById(@PathVariable(ID) long id) {
        return ResponseEntity.ok().body(convertToDto(presentationService.getById(id)));
    }

    @PostMapping("/")
    public ResponseEntity<PresentationResponse> create(@RequestBody PresentationRequest request) {
        return ResponseEntity.ok().body(convertToDto(presentationService.create(request)));
    }

    private PresentationResponse convertToDto(Presentation presentation) {
        return PresentationResponse.builder()
                .id(presentation.getId())
                .name(presentation.getName())
                .description(presentation.getDescription())
                .creationTime(presentation.getCreationTime().toString())
                .startTime(presentation.getStartTime().toString())
                .authorLogin(presentation.getAuthor().getLogin())
                .build();
    }
}
package com.example.presentationvotingservice.controller;

import com.example.presentationvotingservice.dto.request.PresentationRequest;
import com.example.presentationvotingservice.dto.response.PresentationResponse;
import com.example.presentationvotingservice.entity.Presentation;
import com.example.presentationvotingservice.service.PresentationService;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .map(this::convertPresentationToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<PresentationResponse> getById(@PathVariable(ID) long id) {
        return ResponseEntity.ok().body(convertPresentationToDto(presentationService.getById(id)));
    }

    @GetMapping("/filter")
    public ResponseEntity<PresentationResponse> getByName(@RequestParam(name = "name", required = false) String name) {
        return ResponseEntity.ok().body(convertPresentationToDto(presentationService.getByName(name)));
    }

    @PostMapping("/")
    public ResponseEntity<PresentationResponse> create(@RequestBody PresentationRequest request) {
        return ResponseEntity.ok().body(convertPresentationToDto(presentationService.create(request)));
    }

    private PresentationResponse convertPresentationToDto(Presentation presentation) {
        val startTime = presentation.getStartTime();
        return PresentationResponse.builder()
                .id(presentation.getId())
                .name(presentation.getName())
                .description(presentation.getDescription())
                .creationTime(presentation.getCreationTime().toString())
                .startTime(startTime == null ? "" : startTime.toString())
                .authorLogin(presentation.getClient().getLogin())
                .build();
    }
}
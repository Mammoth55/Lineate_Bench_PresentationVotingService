package com.lineate.presentationvotingservice.controller;

import com.lineate.pojolib.dto.request.PresentationRequest;
import com.lineate.pojolib.dto.response.PresentationResponse;
import com.lineate.presentationvotingservice.entity.Presentation;
import com.lineate.presentationvotingservice.service.PresentationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<PresentationResponse>> getAll() {
        return ResponseEntity.ok().body(presentationService.getAll().stream()
                .map(this::convertPresentationToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/allPublished")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<PresentationResponse>> getAllPublished() {
        return ResponseEntity.ok().body(presentationService.getAllPublished().stream()
                .map(this::convertPresentationToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id:[\\d]+}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<PresentationResponse> getById(@PathVariable(ID) long id) {
        return ResponseEntity.ok().body(convertPresentationToDto(presentationService.getById(id)));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<PresentationResponse> getByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok().body(convertPresentationToDto(presentationService.getByName(name)));
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<PresentationResponse> create(@RequestBody PresentationRequest request) {
        return ResponseEntity.ok().body(convertPresentationToDto(presentationService.create(request)));
    }

    @GetMapping("/publish")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<PresentationResponse> publishByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok().body(convertPresentationToDto(presentationService.publishByName(name)));
    }

    @GetMapping("/planning")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<PresentationResponse> planningByName(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime) {
        return ResponseEntity.ok().body(convertPresentationToDto(presentationService.planningByName(name, startTime)));
    }

    private PresentationResponse convertPresentationToDto(Presentation presentation) {
        ZonedDateTime startTime = presentation.getStartTime();
        return PresentationResponse.builder()
                .id(presentation.getId())
                .name(presentation.getName())
                .description(presentation.getDescription())
                .status(presentation.getStatus().name())
                .creationTime(presentation.getCreationTime().toString())
                .startTime(startTime == null ? "" : startTime.toString())
                .authorLogin(presentation.getClient().getLogin())
                .build();
    }
}
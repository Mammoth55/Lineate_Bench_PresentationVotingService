package com.example.presentationvotingservice.controller;

import com.example.presentationvotingservice.dto.response.UserResponse;
import com.example.presentationvotingservice.dto.request.UserRequest;
import com.example.presentationvotingservice.model.Client;
import com.example.presentationvotingservice.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class ClientController {

    private static final String ID = "id";
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok().body(clientService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<UserResponse> getById(@PathVariable(ID) long id) {
        return ResponseEntity.ok().body(convertToDto(clientService.getById(id)));
    }

    @GetMapping("/filter")
    public ResponseEntity<UserResponse> getByLogin(@RequestParam(name = "login", required = false) String login) {
        return ResponseEntity.ok().body(convertToDto(clientService.getByLogin(login)));
    }

    @PostMapping("/")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        return ResponseEntity.ok().body(convertToDto(clientService.create(request)));
    }

    private UserResponse convertToDto(Client client) {
        return UserResponse.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .login(client.getLogin())
                .password(client.getPassword())
                .build();
    }
}
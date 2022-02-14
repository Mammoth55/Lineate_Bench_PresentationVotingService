package com.example.presentationvotingservice.controller;

import com.example.presentationvotingservice.dto.response.ClientResponse;
import com.example.presentationvotingservice.dto.request.ClientRequest;
import com.example.presentationvotingservice.entity.Client;
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
    public ResponseEntity<List<ClientResponse>> getAll() {
        return ResponseEntity.ok().body(clientService.getAll().stream()
                .map(this::convertClientToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<ClientResponse> getById(@PathVariable(ID) long id) {
        return ResponseEntity.ok().body(convertClientToDto(clientService.getById(id)));
    }

    @GetMapping("/filter")
    public ResponseEntity<ClientResponse> getByLogin(@RequestParam(name = "login", required = false) String login) {
        return ResponseEntity.ok().body(convertClientToDto(clientService.getByLogin(login)));
    }

    @PostMapping("/")
    public ResponseEntity<ClientResponse> create(@RequestBody ClientRequest request) {
        return ResponseEntity.ok().body(convertClientToDto(clientService.create(request)));
    }

    private ClientResponse convertClientToDto(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .login(client.getLogin())
                .build();
    }
}
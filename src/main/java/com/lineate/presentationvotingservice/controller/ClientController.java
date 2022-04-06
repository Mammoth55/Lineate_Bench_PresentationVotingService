package com.lineate.presentationvotingservice.controller;

import com.lineate.pojolib.dto.request.ClientRequest;
import com.lineate.pojolib.dto.response.ClientResponse;
import com.lineate.presentationvotingservice.entity.Client;
import com.lineate.presentationvotingservice.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private static final String ID = "id";
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<ClientResponse>> getAll() {
        return ResponseEntity.ok().body(clientService.getAll().stream()
                .map(this::convertClientToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id:[\\d]+}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<ClientResponse> getById(@PathVariable(ID) long id) {
        return ResponseEntity.ok().body(convertClientToDto(clientService.getById(id)));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<ClientResponse> getByLogin(@RequestParam(name = "login") String login) {
        return ResponseEntity.ok().body(convertClientToDto(clientService.getByLogin(login)));
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<ClientResponse> create(@RequestBody ClientRequest request) {
        return ResponseEntity.ok().body(convertClientToDto(clientService.create(request)));
    }

    private ClientResponse convertClientToDto(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .login(client.getLogin())
                .role(client.getRole().name())
                .status(client.getStatus().name())
                .build();
    }
}
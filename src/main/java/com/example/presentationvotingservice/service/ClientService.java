package com.example.presentationvotingservice.service;

import com.example.presentationvotingservice.dto.request.UserRequest;
import com.example.presentationvotingservice.model.Client;
import com.example.presentationvotingservice.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getById(long id) {
        return clientRepository.getById(id);
    }

    public Client getByLogin(String login) {
        return clientRepository.getByLogin(login);
    }

    public Client create(UserRequest request) {
        return clientRepository.save(Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .login(request.getLogin())
                .password(request.getPassword())
                .build());
    }
}
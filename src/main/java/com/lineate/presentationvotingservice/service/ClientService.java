package com.lineate.presentationvotingservice.service;

import com.lineate.pojolib.dto.request.ClientRequest;
import com.lineate.presentationvotingservice.entity.Client;
import com.lineate.presentationvotingservice.repository.ClientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public Client create(ClientRequest request) {
        return clientRepository.save(Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .login(request.getLogin())
                .password(request.getPassword())
                .build());
    }
}
package com.example.presentationvotingservice.service;

import com.example.presentationvotingservice.dto.request.PresentationRequest;
import com.example.presentationvotingservice.entity.Presentation;
import com.example.presentationvotingservice.entity.Client;
import com.example.presentationvotingservice.model.PresentationStatus;
import com.example.presentationvotingservice.repository.PresentationRepository;
import com.example.presentationvotingservice.repository.ClientRepository;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class PresentationService {

    private final PresentationRepository presentationRepository;
    private final ClientRepository clientRepository;

    public PresentationService(PresentationRepository presentationRepository,
                               ClientRepository clientRepository) {
        this.presentationRepository = presentationRepository;
        this.clientRepository = clientRepository;
    }

    public List<Presentation> getAll() {
        return presentationRepository.findAll();
    }

    public Presentation getById(long id) {
        return presentationRepository.getById(id);
    }

    public Presentation getByName(String name) {
        return presentationRepository.getByName(name);
    }

    @Transactional
    public Presentation create(PresentationRequest presentationRequest) {
        String authorLogin = presentationRequest.getAuthorLogin();
        Client client = clientRepository.getByLogin(authorLogin);
        if (client == null) {
            client = clientRepository.save(Client.builder()
                    .firstName(authorLogin)
                    .login(authorLogin)
                    .build());
        }
        return presentationRepository.save(Presentation.builder()
                .name(presentationRequest.getName())
                .description(presentationRequest.getDescription())
                .status(PresentationStatus.CREATED)
                .client(client)
                .build());
    }
}
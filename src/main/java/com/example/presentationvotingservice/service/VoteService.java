package com.example.presentationvotingservice.service;

import com.example.presentationvotingservice.dto.request.VoteRequest;
import com.example.presentationvotingservice.entity.Vote;
import com.example.presentationvotingservice.model.VoteStatus;
import com.example.presentationvotingservice.repository.ClientRepository;
import com.example.presentationvotingservice.repository.PresentationRepository;
import com.example.presentationvotingservice.repository.VoteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final ClientRepository clientRepository;
    private final PresentationRepository presentationRepository;

    public VoteService(VoteRepository voteRepository,
                       ClientRepository clientRepository,
                       PresentationRepository presentationRepository) {
        this.voteRepository = voteRepository;
        this.clientRepository = clientRepository;
        this.presentationRepository = presentationRepository;
    }

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public Vote getById(long id) {
        return voteRepository.getById(id);
    }

    @Transactional
    public Vote create(VoteRequest voteRequest) {
        return voteRepository.save(Vote.builder()
                .voteStatus(VoteStatus.valueOf(voteRequest.getVoteStatus()))
                .client(clientRepository.getByLogin(voteRequest.getClientLogin()))
                .presentation(presentationRepository.getByName(voteRequest.getPresentationName()))
                .build());
    }
}
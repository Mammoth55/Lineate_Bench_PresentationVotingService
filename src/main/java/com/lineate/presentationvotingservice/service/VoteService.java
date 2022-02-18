package com.lineate.presentationvotingservice.service;

import com.lineate.pojolib.dto.request.VoteRequest;
import com.lineate.presentationvotingservice.entity.Vote;
import com.lineate.presentationvotingservice.model.VoteStatus;
import com.lineate.presentationvotingservice.repository.ClientRepository;
import com.lineate.presentationvotingservice.repository.PresentationRepository;
import com.lineate.presentationvotingservice.repository.VoteRepository;
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
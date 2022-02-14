package com.example.presentationvotingservice.repository;

import com.example.presentationvotingservice.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>, JpaSpecificationExecutor<Vote> {
}
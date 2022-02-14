package com.example.presentationvotingservice.repository;

import com.example.presentationvotingservice.entity.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PresentationRepository extends JpaRepository<Presentation, Long>, JpaSpecificationExecutor<Presentation> {

    Presentation getByName(String name);
}
package com.example.presentationvotingservice.repository;

import com.example.presentationvotingservice.entity.Presentation;
import com.example.presentationvotingservice.model.PresentationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PresentationRepository extends JpaRepository<Presentation, Long>, JpaSpecificationExecutor<Presentation> {

    Presentation getByName(String name);

    @Modifying
    @Query("update Presentation p set p.status = :status where p.id = :id")
    void setStatusById(@Param("id") long id, @Param("status") PresentationStatus status);

    @Query("SELECT p FROM Presentation p WHERE p.status = :status")
    List<Presentation> getAllByStatus(@Param("status") PresentationStatus status);
}
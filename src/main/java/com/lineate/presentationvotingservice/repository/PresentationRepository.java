package com.lineate.presentationvotingservice.repository;

import com.lineate.presentationvotingservice.entity.Presentation;
import com.lineate.presentationvotingservice.model.PresentationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface PresentationRepository extends JpaRepository<Presentation, Long>, JpaSpecificationExecutor<Presentation> {

    Presentation getByName(String name);

    @Modifying
    @Query("update Presentation p set p.status = :status, p.startTime = :startTime where p.id = :id")
    void updateById(@Param("id") long id,
                    @Param("status") PresentationStatus status,
                    @Param("startTime") ZonedDateTime startTime);

    @Query("SELECT p FROM Presentation p WHERE p.status = :status")
    List<Presentation> getAllByStatus(@Param("status") PresentationStatus status);
}
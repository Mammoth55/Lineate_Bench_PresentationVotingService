package com.example.presentationvotingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Presentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(name="creation_time")
    private ZonedDateTime creationTime;

    @Column(name="start_time")
    private ZonedDateTime startTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Client author;
}
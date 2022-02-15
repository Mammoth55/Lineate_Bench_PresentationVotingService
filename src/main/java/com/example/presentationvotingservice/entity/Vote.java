package com.example.presentationvotingservice.entity;

import com.example.presentationvotingservice.model.VoteStatus;
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
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="vote_status", columnDefinition = "varchar(64) not null default 'NOT_INTERESTED'")
    private VoteStatus voteStatus;

    @Column(name="vote_time", columnDefinition = "timestamp with time zone not null default now()")
    private ZonedDateTime voteTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "presentation_id", nullable = false)
    private Presentation presentation;
}
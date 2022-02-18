package com.lineate.presentationvotingservice.entity;

import com.lineate.presentationvotingservice.model.PresentationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Presentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(64) not null unique")
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(64) not null default 'CREATED'")
    private PresentationStatus status;

    @CreationTimestamp
    @Column(name="creation_time", columnDefinition = "timestamp with time zone not null default now()")
    private ZonedDateTime creationTime;

    @Column(name="start_time", columnDefinition = "timestamp with time zone")
    private ZonedDateTime startTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "presentation")
    private List<Vote> votes;
}
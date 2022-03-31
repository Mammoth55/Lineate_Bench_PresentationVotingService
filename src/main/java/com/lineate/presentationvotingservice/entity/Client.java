package com.lineate.presentationvotingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name", columnDefinition = "varchar(64) not null")
    private String firstName;

    @Column(name="last_name", columnDefinition = "varchar(64)")
    private String lastName;

    @Column(columnDefinition = "varchar(64) not null")
    private String login;

    @Column(columnDefinition = "varchar(64) not null default '123456'")
    private String password;

    @OneToMany(mappedBy = "client")
    private List<Presentation> presentations;

    @OneToMany(mappedBy = "client")
    private List<Vote> votes;
}
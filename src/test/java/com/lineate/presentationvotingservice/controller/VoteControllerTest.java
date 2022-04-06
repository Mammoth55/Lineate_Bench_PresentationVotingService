package com.lineate.presentationvotingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lineate.pojolib.dto.request.VoteRequest;
import com.lineate.presentationvotingservice.entity.Client;
import com.lineate.presentationvotingservice.entity.Presentation;
import com.lineate.presentationvotingservice.entity.Vote;
import com.lineate.presentationvotingservice.model.PresentationStatus;
import com.lineate.presentationvotingservice.model.VoteStatus;
import com.lineate.presentationvotingservice.service.VoteService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class VoteControllerTest {

    final static String TEST_PRESENTATION_NAME = "Hibernate";
    final static String TEST_CLIENT_LOGIN = "11111@gmail.com";
    final static Client TEST_AUTHOR = Client.builder()
            .id(3L)
            .firstName("111")
            .lastName("222")
            .login(TEST_CLIENT_LOGIN)
            .password("123")
            .build();
    final static Client TEST_CLIENT1 = Client.builder()
            .id(3L)
            .firstName("333")
            .lastName("444")
            .login("0" + TEST_CLIENT_LOGIN)
            .password("321")
            .build();
    final static Client TEST_CLIENT2 = Client.builder()
            .id(3L)
            .firstName("555")
            .lastName("666")
            .login("00" + TEST_CLIENT_LOGIN)
            .password("789")
            .build();
    final static Presentation TEST_PRESENTATION = Presentation.builder()
            .id(9L)
            .name(TEST_PRESENTATION_NAME)
            .description(TEST_PRESENTATION_NAME + " in action")
            .status(PresentationStatus.CREATED)
            .creationTime(ZonedDateTime.now())
            .client(TEST_AUTHOR)
            .build();
    final static Vote TEST_VOTE1 = Vote.builder()
            .id(6L)
            .voteStatus(VoteStatus.WANT_LOOK)
            .voteTime(ZonedDateTime.now())
            .client(TEST_CLIENT1)
            .presentation(TEST_PRESENTATION)
            .build();
    final static Vote TEST_VOTE2 = Vote.builder()
            .id(7L)
            .voteStatus(VoteStatus.CANT_LOOK)
            .voteTime(ZonedDateTime.now())
            .client(TEST_CLIENT2)
            .presentation(TEST_PRESENTATION)
            .build();

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private VoteService voteService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new VoteController(voteService)).build();
    }

    @Test
    public void getAllTest() throws Exception {
        List<Vote> votes = List.of(TEST_VOTE1, TEST_VOTE2);

        when(voteService.getAll()).thenReturn(votes);

        mockMvc.perform(get("/api/votes/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].clientLogin", Matchers.equalTo(TEST_VOTE1.getClient().getLogin())))
                .andExpect(jsonPath("$[1].clientLogin", Matchers.equalTo(TEST_VOTE2.getClient().getLogin())));
    }

    @Test
    public void getByIdTest() throws Exception {
        when(voteService.getById(7)).thenReturn(TEST_VOTE2);

        mockMvc.perform(get("/api/votes/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(7)))
                .andExpect(jsonPath("$.clientLogin", Matchers.equalTo(TEST_VOTE2.getClient().getLogin())));
    }

    @Test
    public void createTest() throws Exception {
        when(voteService.create(any())).thenReturn(TEST_VOTE2);

        mockMvc.perform(post("/api/votes/")
                        .content(objectMapper.writeValueAsString(VoteRequest.builder()
                                .voteStatus(TEST_VOTE2.getVoteStatus().name())
                                .clientLogin(TEST_VOTE2.getClient().getLogin())
                                .presentationName(TEST_VOTE2.getPresentation().getName())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(7)))
                .andExpect(jsonPath("$.clientLogin", Matchers.equalTo(TEST_VOTE2.getClient().getLogin())))
                .andExpect(jsonPath("$.presentationName", Matchers.equalTo(TEST_VOTE2.getPresentation().getName())));
    }
}
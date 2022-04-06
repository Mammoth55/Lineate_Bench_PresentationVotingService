package com.lineate.presentationvotingservice.service;

import com.lineate.pojolib.dto.request.VoteRequest;
import com.lineate.presentationvotingservice.entity.Client;
import com.lineate.presentationvotingservice.entity.Presentation;
import com.lineate.presentationvotingservice.entity.Vote;
import com.lineate.presentationvotingservice.model.PresentationStatus;
import com.lineate.presentationvotingservice.model.VoteStatus;
import com.lineate.presentationvotingservice.repository.ClientRepository;
import com.lineate.presentationvotingservice.repository.PresentationRepository;
import com.lineate.presentationvotingservice.repository.VoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceTest {

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

    private VoteService voteService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PresentationRepository presentationRepository;

    @Before
    public void setUp() {
        voteService = new VoteService(voteRepository, clientRepository, presentationRepository);
    }

    @Test
    public void getAllTest() {
        List<Vote> votes = List.of(TEST_VOTE1, TEST_VOTE2);
        when(voteRepository.findAll()).thenReturn(votes);

        List<Vote> actual = voteService.getAll();

        assertEquals(actual.size(), 2);
        assertEquals(actual.get(0).getClient().getLogin(), TEST_VOTE1.getClient().getLogin());
        assertEquals(actual.get(1).getClient().getLogin(), TEST_VOTE2.getClient().getLogin());
    }

    @Test
    public void getByIdTest() {
        when(voteRepository.getById(7L)).thenReturn(TEST_VOTE2);

        Vote actual = voteService.getById(7);

        assertEquals((long) actual.getId(), 7L);
        assertEquals(actual.getClient().getLogin(), TEST_VOTE2.getClient().getLogin());
    }

    @Test
    public void createTest() {
        when(voteRepository.save(any())).thenReturn(TEST_VOTE2);

        Vote actual = voteService.create(VoteRequest.builder()
                .voteStatus(TEST_VOTE2.getVoteStatus().name())
                .clientLogin(TEST_VOTE2.getClient().getLogin())
                .presentationName(TEST_VOTE2.getPresentation().getName())
                .build());

        assertNotNull(actual);
        assertEquals(actual.getClient().getLogin(), TEST_VOTE2.getClient().getLogin());
        assertEquals(actual.getPresentation().getName(), TEST_VOTE2.getPresentation().getName());
    }
}
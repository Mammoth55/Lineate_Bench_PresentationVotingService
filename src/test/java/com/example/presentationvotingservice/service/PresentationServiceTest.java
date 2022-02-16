package com.example.presentationvotingservice.service;

import com.example.presentationvotingservice.dto.request.PresentationRequest;
import com.example.presentationvotingservice.entity.Client;
import com.example.presentationvotingservice.entity.Presentation;
import com.example.presentationvotingservice.model.PresentationStatus;
import com.example.presentationvotingservice.repository.ClientRepository;
import com.example.presentationvotingservice.repository.PresentationRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PresentationServiceTest {

    final static String TEST_PRESENTATION1_NAME = "Hibernate";
    final static String TEST_PRESENTATION2_NAME = "Concurrency";
    final static String TEST_CLIENT_LOGIN = "11111@gmail.com";
    final static Client TEST_CLIENT = Client.builder()
            .id(5)
            .firstName("111")
            .lastName("222")
            .login(TEST_CLIENT_LOGIN)
            .password("321")
            .build();
    final static Presentation TEST_PRESENTATION1 = Presentation.builder()
            .id(8L)
            .name(TEST_PRESENTATION1_NAME)
            .description(TEST_PRESENTATION1_NAME + " in action")
            .status(PresentationStatus.PUBLISHED)
            .creationTime(ZonedDateTime.now())
            .client(TEST_CLIENT)
            .build();
    final static Presentation TEST_PRESENTATION2 = Presentation.builder()
            .id(9L)
            .name(TEST_PRESENTATION2_NAME)
            .description(TEST_PRESENTATION2_NAME + " in action")
            .status(PresentationStatus.PUBLISHED)
            .creationTime(ZonedDateTime.now())
            .client(TEST_CLIENT)
            .build();

    private PresentationService presentationService;

    @Mock
    private PresentationRepository presentationRepository;

    @Mock
    private ClientRepository clientRepository;

    @Before
    public void setUp() {
        presentationService = new PresentationService(presentationRepository, clientRepository);
    }

    @Test
    public void getAllTest() {
        List<Presentation> presentations = List.of(TEST_PRESENTATION1, TEST_PRESENTATION2);
        when(presentationRepository.findAll()).thenReturn(presentations);

        List<Presentation> actual = presentationService.getAll();

        assertEquals(actual.size(), 2);
        assertEquals(actual.get(0).getName(), TEST_PRESENTATION1_NAME);
        assertEquals(actual.get(1).getName(), TEST_PRESENTATION2_NAME);
    }

    @Test
    public void getAllPublishedTest() {
        List<Presentation> presentations = List.of(TEST_PRESENTATION1, TEST_PRESENTATION2);
        when(presentationRepository.getAllByStatus(PresentationStatus.PUBLISHED)).thenReturn(presentations);

        List<Presentation> actual = presentationService.getAllPublished();

        assertEquals(actual.size(), 2);
        assertEquals(actual.get(0).getName(), TEST_PRESENTATION1_NAME);
        assertEquals(actual.get(1).getName(), TEST_PRESENTATION2_NAME);
    }

    @Test
    public void getByIdTest() {
        when(presentationRepository.getById(9L)).thenReturn(TEST_PRESENTATION2);

        Presentation actual = presentationService.getById(9);

        assertEquals((long) actual.getId(), 9L);
        assertEquals(actual.getName(), TEST_PRESENTATION2_NAME);
    }

    @Test
    public void getByNameTest() {
        when(presentationRepository.getByName(TEST_PRESENTATION2_NAME)).thenReturn(TEST_PRESENTATION2);

        Presentation actual = presentationService.getByName(TEST_PRESENTATION2_NAME);

        assertEquals((long) actual.getId(), 9L);
        assertEquals(actual.getName(), TEST_PRESENTATION2_NAME);
    }

    @Test
    public void createTest() {
        when(presentationRepository.save(any())).thenReturn(TEST_PRESENTATION2);

        Presentation actual = presentationService.create(PresentationRequest.builder()
                        .name(TEST_PRESENTATION2.getName())
                        .description(TEST_PRESENTATION2.getDescription())
                        .authorLogin(TEST_PRESENTATION2.getClient().getLogin())
                .build());

        assertNotNull(actual);
        assertEquals(actual.getClient().getLogin(), TEST_PRESENTATION2.getClient().getLogin());
        assertEquals(actual.getName(), TEST_PRESENTATION2_NAME);
    }

    @Test
    public void publishByNameTest() {
        TEST_PRESENTATION2.setStatus(PresentationStatus.CREATED);
        when(presentationRepository.getByName(TEST_PRESENTATION2_NAME)).thenReturn(TEST_PRESENTATION2);

        Presentation actual = presentationService.publishByName(TEST_PRESENTATION2_NAME);

        assertEquals((long) actual.getId(), 9L);
        assertEquals(actual.getStatus(), PresentationStatus.PUBLISHED);
        assertEquals(actual.getName(), TEST_PRESENTATION2_NAME);
    }
}
package com.example.presentationvotingservice.service;

import com.example.presentationvotingservice.dto.request.ClientRequest;
import com.example.presentationvotingservice.entity.Client;
import com.example.presentationvotingservice.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    final static String TEST_CLIENT1_LOGIN = "11111@gmail.com";
    final static String TEST_CLIENT2_LOGIN = "22222@gmail.com";
    final static Client TEST_CLIENT1 = Client.builder()
            .id(5)
            .firstName("111")
            .lastName("222")
            .login(TEST_CLIENT1_LOGIN)
            .password("321")
            .build();
    final static Client TEST_CLIENT2 = Client.builder()
            .id(6)
            .firstName("222")
            .lastName("111")
            .login(TEST_CLIENT2_LOGIN)
            .password("123")
            .build();

    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Before
    public void setUp() {
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void getAllTest() {
        List<Client> clients = List.of(TEST_CLIENT1, TEST_CLIENT2);
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> actual = clientService.getAll();

        assertEquals(actual.size(), 2);
        assertEquals(actual.get(0).getLogin(), TEST_CLIENT1_LOGIN);
        assertEquals(actual.get(1).getLogin(), TEST_CLIENT2_LOGIN);
    }

    @Test
    public void getByIdTest() {
        when(clientRepository.getById(6L)).thenReturn(TEST_CLIENT2);

        Client actual = clientService.getById(6L);

        assertNotNull(actual);
        assertEquals(actual.getId(), 6);
        assertEquals(actual.getLogin(), TEST_CLIENT2_LOGIN);
    }

    @Test
    public void getByLoginTest() {
        when(clientRepository.getByLogin(TEST_CLIENT2_LOGIN)).thenReturn(TEST_CLIENT2);

        Client actual = clientService.getByLogin(TEST_CLIENT2_LOGIN);

        assertNotNull(actual);
        assertEquals(actual.getId(), 6);
        assertEquals(actual.getLogin(), TEST_CLIENT2_LOGIN);
    }

    @Test
    public void createTest() {
        when(clientRepository.save(any())).thenReturn(TEST_CLIENT2);

        Client actual = clientService.create(ClientRequest.builder()
                .firstName(TEST_CLIENT2.getFirstName())
                .lastName(TEST_CLIENT2.getLastName())
                .login(TEST_CLIENT2.getLogin())
                .password(TEST_CLIENT2.getPassword())
                .build());

        assertNotNull(actual);
        assertEquals(actual.getFirstName(), TEST_CLIENT2.getFirstName());
        assertEquals(actual.getLogin(), TEST_CLIENT2_LOGIN);
    }
}
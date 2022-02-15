package com.example.presentationvotingservice.controller;

import com.example.presentationvotingservice.dto.request.ClientRequest;
import com.example.presentationvotingservice.entity.Client;
import com.example.presentationvotingservice.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {

    final static String TEST_CLIENT1_LOGIN = "11111@gmail.com";
    final static String TEST_CLIENT2_LOGIN = "22222@gmail.com";
    final static Client CLIENT1 = Client.builder()
            .id(5)
            .firstName("111")
            .lastName("222")
            .login(TEST_CLIENT1_LOGIN)
            .password("321")
            .build();
    final static Client CLIENT2 = Client.builder()
            .id(6)
            .firstName("222")
            .lastName("111")
            .login(TEST_CLIENT2_LOGIN)
            .password("123")
            .build();

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ClientService clientService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ClientController(clientService)).build();
    }

    @Test
    public void getAllTest() throws Exception {
        List<Client> clients = List.of(CLIENT1, CLIENT2);

        when(clientService.getAll()).thenReturn(clients);

        mockMvc.perform(get("/api/clients/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].login", Matchers.equalTo(TEST_CLIENT1_LOGIN)))
                .andExpect(jsonPath("$[1].login", Matchers.equalTo(TEST_CLIENT2_LOGIN)));
    }

    @Test
    public void getByIdTest() throws Exception {
        when(clientService.getById(5)).thenReturn(CLIENT1);

        mockMvc.perform(get("/api/clients/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(5)))
                .andExpect(jsonPath("$.login", Matchers.equalTo(CLIENT1.getLogin())));
    }

    @Test
    public void getByLoginTest() throws Exception {
        when(clientService.getByLogin(TEST_CLIENT1_LOGIN)).thenReturn(CLIENT1);

        mockMvc.perform(get("/api/clients/filter?login=" + TEST_CLIENT1_LOGIN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(5)))
                .andExpect(jsonPath("$.login", Matchers.equalTo(CLIENT1.getLogin())));
    }

    @Test
    public void createTest() throws Exception {
        when(clientService.create(any())).thenReturn(CLIENT1);

        mockMvc.perform(post("/api/clients/")
                        .content(objectMapper.writeValueAsString(ClientRequest.builder()
                                .firstName(CLIENT1.getFirstName())
                                .lastName(CLIENT1.getLastName())
                                .login(CLIENT1.getLogin())
                                .password(CLIENT1.getPassword())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(5)))
                .andExpect(jsonPath("$.login", Matchers.equalTo(CLIENT1.getLogin())));
    }
}
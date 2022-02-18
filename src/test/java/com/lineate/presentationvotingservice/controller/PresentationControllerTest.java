package com.lineate.presentationvotingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lineate.pojolib.dto.request.PresentationRequest;
import com.lineate.presentationvotingservice.entity.Client;
import com.lineate.presentationvotingservice.entity.Presentation;
import com.lineate.presentationvotingservice.model.PresentationStatus;
import com.lineate.presentationvotingservice.service.PresentationService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PresentationControllerTest {

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

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private PresentationService presentationService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PresentationController(presentationService)).build();
    }

    @Test
    public void getAllTest() throws Exception {
        List<Presentation> presentations = List.of(TEST_PRESENTATION1, TEST_PRESENTATION2);

        when(presentationService.getAll()).thenReturn(presentations);

        mockMvc.perform(get("/api/presentations/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo(TEST_PRESENTATION1_NAME)))
                .andExpect(jsonPath("$[1].name", Matchers.equalTo(TEST_PRESENTATION2_NAME)));
    }

    @Test
    public void getAllPublishedTest() throws Exception {
        List<Presentation> presentations = List.of(TEST_PRESENTATION1, TEST_PRESENTATION2);

        when(presentationService.getAllPublished()).thenReturn(presentations);

        mockMvc.perform(get("/api/presentations/allPublished/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo(TEST_PRESENTATION1_NAME)))
                .andExpect(jsonPath("$[1].name", Matchers.equalTo(TEST_PRESENTATION2_NAME)));
    }

    @Test
    public void getByIdTest() throws Exception {
        when(presentationService.getById(9)).thenReturn(TEST_PRESENTATION2);

        mockMvc.perform(get("/api/presentations/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(9)))
                .andExpect(jsonPath("$.name", Matchers.equalTo(TEST_PRESENTATION2_NAME)));
    }

    @Test
    public void getByNameTest() throws Exception {
        when(presentationService.getByName(TEST_PRESENTATION2_NAME)).thenReturn(TEST_PRESENTATION2);

        mockMvc.perform(get("/api/presentations/filter?name=" + TEST_PRESENTATION2_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(9)))
                .andExpect(jsonPath("$.name", Matchers.equalTo(TEST_PRESENTATION2_NAME)));
    }

    @Test
    public void createTest() throws Exception {
        when(presentationService.create(any())).thenReturn(TEST_PRESENTATION2);

        mockMvc.perform(post("/api/presentations/")
                        .content(objectMapper.writeValueAsString(PresentationRequest.builder()
                                .name(TEST_PRESENTATION2_NAME)
                                .description(TEST_PRESENTATION2.getDescription())
                                .authorLogin(TEST_CLIENT_LOGIN)
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(9)))
                .andExpect(jsonPath("$.authorLogin", Matchers.equalTo(TEST_CLIENT_LOGIN)))
                .andExpect(jsonPath("$.name", Matchers.equalTo(TEST_PRESENTATION2_NAME)));
    }

    @Test
    public void publishByNameTest() throws Exception {
        TEST_PRESENTATION2.setStatus(PresentationStatus.PUBLISHED);
        when(presentationService.publishByName(TEST_PRESENTATION2_NAME)).thenReturn(TEST_PRESENTATION2);

        mockMvc.perform(get("/api/presentations/publish?name=" + TEST_PRESENTATION2_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(9)))
                .andExpect(jsonPath("$.status", Matchers.equalTo(PresentationStatus.PUBLISHED.name())))
                .andExpect(jsonPath("$.name", Matchers.equalTo(TEST_PRESENTATION2_NAME)));
    }

    @Test
    public void planningByNameTest() throws Exception {
        TEST_PRESENTATION2.setStatus(PresentationStatus.PLANNED);
        ZonedDateTime startTime = ZonedDateTime.of(2022, 5, 19, 15, 30, 0, 0, ZoneId.of("UTC"));
        TEST_PRESENTATION2.setStartTime(startTime);
        when(presentationService.planningByName(any(), any())).thenReturn(TEST_PRESENTATION2);

        mockMvc.perform(get("/api/presentations/planning?name=" + TEST_PRESENTATION2_NAME
                + "&startTime=" + startTime))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(9)))
                .andExpect(jsonPath("$.startTime", Matchers.equalTo(startTime.toString())))
                .andExpect(jsonPath("$.status", Matchers.equalTo(PresentationStatus.PLANNED.name())))
                .andExpect(jsonPath("$.name", Matchers.equalTo(TEST_PRESENTATION2_NAME)));
    }
}
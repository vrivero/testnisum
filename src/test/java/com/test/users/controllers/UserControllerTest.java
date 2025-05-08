package com.test.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.users.configs.security.JwtAuthenticationFilter;
import com.test.users.configs.security.JwtTokenUtil;
import com.test.users.dtos.request.UserRequest;
import com.test.users.dtos.response.UserResponse;
import com.test.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    void testListAll() throws Exception {
        // Preparar la respuesta simulada
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("users", Collections.emptyList());
        mockResponse.put("page", 0);
        mockResponse.put("size", 20);
        mockResponse.put("totalElem", 0);
        mockResponse.put("totalPage", 1);

        when(userService.listAllUsers(0, 20, "id", "asc", "")).thenReturn(mockResponse);

        // Realizar la solicitud GET
        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("size", "20")
                        .param("sortBy", "id")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void testCreate() throws Exception {
        // Preparar la solicitud
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("john.doe@example.com");
        userRequest.setPassword("Password123*");

        // Preparar la respuesta simulada
        UserResponse mockResponse = new UserResponse();
        mockResponse.setId("12345");
        mockResponse.setName("John Doe");
        mockResponse.setEmail("john.doe@example.com");

        when(userService.createUser(userRequest)).thenReturn(mockResponse);

        // Realizar la solicitud POST
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Usuario creado exitosamente"))
                .andExpect(jsonPath("$.data").exists());
    }

    @BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new JwtAuthenticationFilter(jwtTokenUtil))
                .build();
    }

    private String obtainToken(String username, String password) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        ResultActions result = mockMvc.perform(post("/users/login")
                        .params(params)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
    }
}
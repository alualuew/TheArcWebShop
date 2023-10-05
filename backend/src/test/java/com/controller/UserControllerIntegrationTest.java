package com.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import com.config.SecurityConfig;
import com.dto.UserDTO;
import com.model.User;
import com.repository.UserRepository;
import com.service.TokenService;
import com.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
@Import({UserService.class, SecurityConfig.class})
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllUserWithAdminRole() throws Exception {
        // Create dummy user data for the mock
        List<User> dummyUsers = new ArrayList<>();
        dummyUsers.add(new User());
        dummyUsers.add(new User());
        Gson gson = new Gson();

        // Mock the userRepository method
        when(userRepository.findAll()).thenReturn(dummyUsers);

        // Execute the HTTP GET request and validate the response
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/users"))
                .andExpect(status().isOk())
                .andExpect(result -> gson.toJson(dummyUsers));
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setActive(true);
        userDTO.setAdmin(false);
        userDTO.setUsername("testUsername");
        userDTO.setPassword("testPassword");
        userDTO.setEmail("test@test.at");

        User user = new User();
        Gson gson = new Gson();

        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // Execute the HTTP POST request and validate the response
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/users/createUser")
                .content(gson.toJson(userDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> gson.toJson(user));
    }
}
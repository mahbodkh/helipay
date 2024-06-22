package app.helipay.um.api;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import app.helipay.um.service.UserService;
import app.helipay.um.service.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


class UserManagerApiTest {


    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @MockBean
    private UserService userService;

    @Test
    void testRegisterUser() throws Exception {
        final var request = buildRegisterRequest();
        final var expectedResponse = buildUserReply();

        when(userService.registerUser(any(RegisterRequest.class), any())).thenReturn(expectedResponse);


        mockMvc.perform(MockMvcRequestBuilders.post("/um/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                //
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedResponse.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(expectedResponse.username()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expectedResponse.email()));
    }

    @Test
    void testLoginUser() throws Exception {
        final var request = buildLoginRequest();
        final var expectedResponse = buildLoginReply();

        when(userService.login(any(LoginRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/um/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                //
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(expectedResponse.token()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.expiresIn").value(expectedResponse.expiration()));
    }

    private LoginReply buildLoginReply() {
        return new LoginReply("example_token", Instant.now().plus(Duration.ofHours(1)));
    }

    @Test
    void testGetUserById() throws Exception {
        // Mock the userService.getUserById method to return a predefined user
        final var user = buildUserReply();
        when(userService.getUserById(anyLong())).thenReturn(user);

        // Perform the GET request to the endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/um/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                // Add assertions for the response body JSON properties
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.username()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.email()));
        // Add more assertions as needed for other properties
    }


    private UserReply buildUserReply() {
        Long id = 1L;
        String username = "john_doe";
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String imageUrl = "https://example.com/profile.jpg";
        boolean isActivated = true;
        String langKey = "en";
        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_SUPER");
        authorities.add("ROLE_ADMIN");

        return new UserReply(id, username, firstName, lastName, email, imageUrl, isActivated, langKey, authorities);
    }

    private RegisterRequest buildRegisterRequest() {
        return new RegisterRequest(
                "john_doe",
                "John",
                "Doe",
                "john.doe@example.com",
                "https://example.com/profile.jpg",
                "en"
        );
    }

    private LoginRequest buildLoginRequest() {
        return new LoginRequest(
                "john_doe",
                "password123",
                "192.168.1.1"
        );
    }


}
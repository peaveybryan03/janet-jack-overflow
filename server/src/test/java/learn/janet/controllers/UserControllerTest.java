package learn.janet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.janet.domain.Result;
import learn.janet.domain.ResultType;
import learn.janet.domain.UserService;
import learn.janet.models.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static learn.janet.TestHelper.userAfterCreate;
import static learn.janet.TestHelper.userToCreate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserService service;

    @Test
    void shouldReturn400WhenNameBlank() throws Exception {
        User user = userToCreate();
        user.setName("");

        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenEmailBlank() throws Exception {
        User user = userToCreate();
        user.setEmail("");

        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenPasswordBlank() throws Exception {
        User user = userToCreate();
        user.setPassword("");

        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn409WhenNameDuplicate() throws Exception {
        Result<User> result = new Result<>();
        result.addErrorMessage("Name is already taken.", ResultType.CONFLICT);

        when(service.create(any(User.class))).thenReturn(result);

        User user = userToCreate();
        user.setName("bryanpeavey");

        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn409WhenEmailDuplicate() throws Exception {
        Result<User> result = new Result<>();
        result.addErrorMessage("Email is already taken.", ResultType.CONFLICT);

        when(service.create(any(User.class))).thenReturn(result);

        User user = userToCreate();
        user.setEmail("peaveybryan03@gmail.com");

        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldCreate() throws Exception {
        Result<User> result = new Result<>();
        result.setPayload(userAfterCreate());

        when(service.create(any(User.class))).thenReturn(result);

        String json = objectMapper.writeValueAsString(userToCreate());

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("janetjackson"));

    }
}
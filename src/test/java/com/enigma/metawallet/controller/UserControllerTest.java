package com.enigma.metawallet.controller;

import com.enigma.metawallet.entity.User;
import com.enigma.metawallet.entity.UserCredential;
import com.enigma.metawallet.entity.Wallet;
import com.enigma.metawallet.model.response.UserResponse;
import com.enigma.metawallet.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void itShouldGetAllUserForAdminWhenGetAllUserSuccessful() throws Exception {
        //dummy user
        UserResponse userResponse = UserResponse.builder()
                .id("1")
                .name("test")
                .address("jalan")
                .city("city")
                .country("country")
                .email("test@gmail.com")
                .username("testing")
                .mobilePhone("90328409")
                .dateOfBirth(LocalDate.of(2023, 12,12).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();

        PageImpl<UserResponse> userResponses = new PageImpl<>(Collections.singletonList(userResponse), PageRequest.of(0, 10), 1);
        when(userService.getAllUserForAdmin(anyInt(), anyInt())).thenReturn(userResponses);

        mockMvc.perform(
                get("/v1/users")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200));

        verify(userService, times(1)).getAllUserForAdmin(10, 0);
    }

    @Test
    void getUserById() {
    }

    @Test
    void topUpWallet() {
    }

    @Test
    void getWalletByUserId() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void changePasswordUser() {
    }

    @Test
    void deleteUserByUserId() {
    }
}
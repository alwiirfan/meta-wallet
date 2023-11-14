package com.enigma.metawallet.controller;

import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.UserResponse;
import com.enigma.metawallet.model.response.WalletResponse;
import com.enigma.metawallet.security.JwtUtils;
import com.enigma.metawallet.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
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

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void itShouldGetAllUserForAdminWhenGetAllUserSuccessful() throws Exception {
        //dummy user
        UserResponse dummyUser1 = UserResponse.builder().id("1").name("test").email("testing@example.com").dateOfBirth("11-11-2021").mobilePhone("089778364").build();
        UserResponse dummyUser2 = UserResponse.builder().id("2").name("arif").email("arif@example.com").dateOfBirth("12-12-2023").mobilePhone("132984713").build();

        List<UserResponse> dummyUsers = Arrays.asList(dummyUser1, dummyUser2);

        when(userService.getAllUserForAdmin(anyInt(), anyInt())).thenReturn(new PageImpl<>(dummyUsers));

        String jwtToken = jwtUtils.generateToken("test@example.com");

        mockMvc.perform(
                get("/v1/users")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully get all users"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].id").value(dummyUser1.getId()))
                .andExpect(jsonPath("$.data[0].name").value(dummyUser1.getName()))
                .andExpect(jsonPath("$.data[0].email").value(dummyUser1.getEmail()))
                .andExpect(jsonPath("$.data[0].dateOfBirth").value(dummyUser1.getDateOfBirth()))
                .andExpect(jsonPath("$.data[0].mobilePhone").value(dummyUser1.getMobilePhone()))
                .andExpect(jsonPath("$.data[1].id").value(dummyUser2.getId()))
                .andExpect(jsonPath("$.data[1].name").value(dummyUser2.getName()))
                .andExpect(jsonPath("$.data[1].email").value(dummyUser2.getEmail()))
                .andExpect(jsonPath("$.data[1].dateOfBirth").value(dummyUser2.getDateOfBirth()))
                .andExpect(jsonPath("$.data[1].mobilePhone").value(dummyUser2.getMobilePhone()));

        verify(userService, times(1)).getAllUserForAdmin(10, 0);
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void itShouldGetDataUserAndSuccessfulWhenGetUserByUserId() throws Exception {
        //dummy user
        UserResponse dummyUser = UserResponse.builder().id("1").name("test").email("testing@example.com").dateOfBirth("11-11-2021").mobilePhone("089778364").build();

        when(userService.getUserById(dummyUser.getId())).thenReturn(dummyUser);

        String jwtToken = jwtUtils.generateToken("testing@example.com");

        mockMvc.perform(
                get("/v1/users/" + dummyUser.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully get user by id with name : " + dummyUser.getName()))
                .andExpect(jsonPath("$.data.id").value(dummyUser.getId()))
                .andExpect(jsonPath("$.data.name").value(dummyUser.getName()))
                .andExpect(jsonPath("$.data.email").value(dummyUser.getEmail()))
                .andExpect(jsonPath("$.data.dateOfBirth").value(dummyUser.getDateOfBirth()))
                .andExpect(jsonPath("$.data.mobilePhone").value(dummyUser.getMobilePhone()));

        verify(userService, times(1)).getUserById(dummyUser.getId());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void itShouldTopUpOnUserWalletWhenTopUpWalletByRequest() throws Exception {
        WalletRequest walletRequest = new WalletRequest("1", 50000L);
        WalletResponse walletResponse = new WalletResponse("1", "2", 49950L);

        when(userService.topUpWallet(walletRequest)).thenReturn(walletResponse);

        String jwtToken = jwtUtils.generateToken("testing@example.com");

        mockMvc.perform(
                patch("/v1/users/wallet")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletRequest))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully top up with Id : " + walletResponse.getUserId()))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.userId").value(walletResponse.getUserId()))
                .andExpect(jsonPath("$.data.walletId").value(walletResponse.getWalletId()))
                .andExpect(jsonPath("$.data.balance").value(walletResponse.getBalance()));

        verify(userService, times(1)).topUpWallet(walletRequest);
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
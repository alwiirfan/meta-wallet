package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.Admin;
import com.enigma.metawallet.entity.User;
import com.enigma.metawallet.entity.UserCredential;
import com.enigma.metawallet.entity.Wallet;
import com.enigma.metawallet.model.request.ChangePasswordRequest;
import com.enigma.metawallet.model.request.UserRequest;
import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.UserResponse;
import com.enigma.metawallet.model.response.WalletResponse;
import com.enigma.metawallet.repository.UserRepository;
import com.enigma.metawallet.service.AdminService;
import com.enigma.metawallet.service.UserCredentialService;
import com.enigma.metawallet.service.UserService;
import com.enigma.metawallet.service.WalletService;
import com.enigma.metawallet.util.AccountUtil;
import com.enigma.metawallet.util.ValidationUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    //MOCK
    //wallet
    private final WalletService walletService = mock(WalletService.class);

    //admin
    private final AdminService adminService = mock(AdminService.class);

    //user credential
    private final UserCredentialService userCredentialService = mock(UserCredentialService.class);

    //validation
    private final ValidationUtil validationUtil = mock(ValidationUtil.class);

    //password encoder
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    //user
    private final UserRepository userRepository = mock(UserRepository.class);

    private final AccountUtil accountUtil = mock(AccountUtil.class);

    @InjectMocks
    private final UserService userService = new UserServiceImpl(userRepository, walletService, adminService,
            userCredentialService, validationUtil, accountUtil, passwordEncoder);

    @Test
    void itShouldReturnUserWhenCreateNewUser() {
        User dummyUser = new User();
        dummyUser.setId("1");
        dummyUser.setEmail("test@example.com");
        dummyUser.setCity("city");
        validationUtil.validate(dummyUser);

        when(userRepository.save(any(User.class))).thenReturn(dummyUser);

        User user = userService.create(dummyUser);

        verify(userRepository, times(1)).save(dummyUser);

        assertEquals(dummyUser.getId(), user.getId());
        assertEquals(dummyUser.getEmail(), user.getEmail());
        assertEquals(dummyUser.getCity(), user.getCity());
    }

    @Test
    void itShouldGetAllDataUsersWhenCallGetAll() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        // list of users
        List<User> dummyUsers = new ArrayList<>();
        dummyUsers.add(new User("test", "test", LocalDate.of(2023, 12, 11) , "test@example.com", "jalan tikus",
                "yoyakarta", "indopride", "0000000", new UserCredential(), new Wallet()));
        dummyUsers.add(new User("testing", "testing", LocalDate.of(2023, 12, 11) , "testing@example.com", "jalan remuk",
                "surabaya", "indo", "3325453234", new UserCredential(), new Wallet()));
        dummyUsers.add(new User("yoi", "yoi", LocalDate.of(2023, 12, 11) , "yoi@example.com", "jalan jalan",
                "jakarta", "indoprider", "324324324", new UserCredential(), new Wallet()));

        Page<User> mockUsersPage = new PageImpl<>(dummyUsers, pageable, dummyUsers.size());

        when(userRepository.findAll(pageable)).thenReturn((mockUsersPage));

        Page<UserResponse> result = userService.getAllUserForAdmin(size, page);

        verify(userRepository, times(1)).findAll(pageable);

        for (int i = 0; i < dummyUsers.size(); i++) {
            assertEquals(dummyUsers.get(i).getId(), result.getContent().get(i).getId());
            assertEquals(dummyUsers.get(i).getAddress(), result.getContent().get(i).getAddress());
            assertEquals(dummyUsers.get(i).getEmail(), result.getContent().get(i).getEmail());
            assertEquals(dummyUsers.get(i).getCity(), result.getContent().get(i).getCity());
            assertEquals(dummyUsers.get(i).getCountry(), result.getContent().get(i).getCountry());
            assertEquals(dummyUsers.get(i).getName(), result.getContent().get(i).getName());
            assertEquals(dummyUsers.get(i).getMobilePhone(), result.getContent().get(i).getMobilePhone());
            assertEquals(
                    dummyUsers.get(i).getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    result.getContent().get(i).getDateOfBirth()
            );

        }

        assertEquals(mockUsersPage.getTotalElements(), result.getTotalElements());
        assertEquals(dummyUsers.size(), result.getContent().size());

    }

    @Test
    void itShouldGetDataUserOneWhenGetUserById() {

        UserDetailImpl userDetail = new UserDetailImpl();
        userDetail.setEmail("test@example.com");

        //dummy user credential
        UserCredential userCredential = new UserCredential();
        userCredential.setEmail("test@example.com");

        //dummy user
        User dummyUser = new User();
        dummyUser.setId("1");
        dummyUser.setEmail("test@example.com");
        dummyUser.setCity("city");
        dummyUser.setUserCredential(userCredential);
        dummyUser.setAddress("jalan yuk");
        dummyUser.setCountry("negara");
        dummyUser.setMobilePhone("098378");
        dummyUser.setName("test");
        dummyUser.setDateOfBirth(LocalDate.of(2023, 12, 12));

        when(accountUtil.blockAccount()).thenReturn(userDetail);
        when(userRepository.findById(dummyUser.getId())).thenReturn(Optional.of(dummyUser));

        UserResponse user = userService.getUserById(dummyUser.getId());
        assertNotNull(user);

        verify(userRepository, times(1)).findById(dummyUser.getId());
        verify(accountUtil, times(1)).blockAccount();

        assertEquals(dummyUser.getId(), user.getId());
        assertEquals(dummyUser.getEmail(), user.getEmail());
        assertEquals(dummyUser.getCity(), user.getCity());
        assertEquals(dummyUser.getCountry(), user.getCountry());
        assertEquals(dummyUser.getName(), user.getName());
        assertEquals(dummyUser.getAddress(), user.getAddress());
        assertEquals(dummyUser.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), user.getDateOfBirth());
        assertEquals(dummyUser.getMobilePhone(), user.getMobilePhone());
    }

    @Test
    void itShouldGetDataWalletAndReturnWalletResponseWhenGetWalletByUserId() {
        UserDetailImpl userDetail = new UserDetailImpl();
        userDetail.setEmail("test@example.com");

        //dummy user credential
        UserCredential userCredential = new UserCredential();
        userCredential.setEmail("test@example.com");

        //dummy user
        User dummyUser = new User();
        dummyUser.setId("1");
        dummyUser.setEmail("test@example.com");
        dummyUser.setCity("city");
        dummyUser.setUserCredential(userCredential);
        dummyUser.setAddress("jalan yuk");
        dummyUser.setCountry("negara");
        dummyUser.setMobilePhone("098378");
        dummyUser.setName("test");
        dummyUser.setDateOfBirth(LocalDate.of(2023, 12, 12));

        //dummy wallet
        Wallet dummyWallet = new Wallet();
        dummyWallet.setId("2");
        dummyWallet.setBalance(50000L);

        when(accountUtil.blockAccount()).thenReturn(userDetail);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(dummyUser));
        when(userRepository.findWalletByUserId(anyString())).thenReturn(dummyWallet);

        WalletResponse response = userService.getWalletByUserId(dummyWallet.getId());
        assertNotNull(response);

        System.out.println("Dummy User ID: " + dummyUser.getId());
        System.out.println("Response User ID: " + response.getUserId());

        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(1)).findWalletByUserId(anyString());
        verify(accountUtil, times(1)).blockAccount();

        assertEquals(dummyWallet.getId(), response.getWalletId());
        assertEquals(dummyUser.getId(), response.getUserId());
        assertEquals(dummyWallet.getBalance(), response.getBalance());
    }

    @Test
    void itShouldBeTopUpWalletByUserIdAndReturnWalletResponse() {
        UserDetailImpl userDetail = new UserDetailImpl();
        userDetail.setEmail("test@example.com");

        //dummy user credential
        UserCredential userCredential = new UserCredential();
        userCredential.setEmail("test@example.com");

        //dummy user
        User dummyUser = new User();
        dummyUser.setId("1");
        dummyUser.setEmail("test@example.com");
        dummyUser.setCity("city");
        dummyUser.setUserCredential(userCredential);
        dummyUser.setAddress("jalan yuk");
        dummyUser.setCountry("negara");
        dummyUser.setMobilePhone("098378");
        dummyUser.setName("test");
        dummyUser.setDateOfBirth(LocalDate.of(2023, 12, 12));

        // dummy wallet request
        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setUserId(dummyUser.getId());
        walletRequest.setBalance(50000L);

        doNothing().when(validationUtil).validate(walletRequest);

        //dummy wallet admin
        Admin dummyAdmin = new Admin();
        dummyAdmin.setId(1L);
        dummyAdmin.setWallet(new Wallet());

        when(accountUtil.blockAccount()).thenReturn(userDetail);
        when(userRepository.findById(dummyUser.getId())).thenReturn(Optional.of(dummyUser));
        doNothing().when(walletService).update(any(Wallet.class));
        when(adminService.getById(anyLong())).thenReturn(dummyAdmin);

        WalletResponse walletResponse = userService.topUpWallet(walletRequest);

        verify(validationUtil, times(1)).validate(walletRequest);
        verify(userRepository, times(1)).findById(dummyUser.getId());
        verify(adminService, times(1)).getById(anyLong());
        verify(walletService, times(1)).update(any(Wallet.class));
        verify(accountUtil, times(1)).blockAccount();
        verify(adminService, times(1)).getById(anyLong());

        assertEquals(dummyUser.getId(), walletResponse.getUserId());
        assertEquals(dummyUser.getWallet().getId(), walletResponse.getWalletId());
        assertEquals(dummyUser.getWallet().getBalance(), walletResponse.getBalance());

    }

    @Test
    void itShouldThrowExceptionAndUserNotFoundWhenTopUpWalletByUserId() {
        // boong
        String userId = "1";
        Long adminId = 1L;

        WalletRequest request = new WalletRequest();
        request.setUserId(userId);
        request.setBalance(50000L);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.topUpWallet(request));

        verify(userRepository, times(1)).findById(userId);
        verify(adminService, never()).getById(adminId);
        verify(walletService, never()).update(any(Wallet.class));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User is not found", exception.getReason());
    }

    @Test
    void itShouldReturnUserResponseWhenUpdateUserByRequest() {
        //dummy request
        UserRequest dummyRequest = new UserRequest();
        dummyRequest.setId("1");
        dummyRequest.setName("test");
        dummyRequest.setAddress("jalanan");
        dummyRequest.setCountry("indo pride");
        dummyRequest.setCity("DIY");
        dummyRequest.setMobilePhone("0898755123");

        //dummy user
        User dummyUser = User.builder()
                .id(dummyRequest.getId())
                .email("test@example.com")
                .address(dummyRequest.getAddress())
                .country(dummyRequest.getCountry())
                .city(dummyRequest.getCity())
                .mobilePhone(dummyRequest.getMobilePhone())
                .name(dummyRequest.getName())
                .userCredential(new UserCredential())
                .wallet(new Wallet())
                .dateOfBirth(LocalDate.of(2023, 11, 12))
                .build();

        when(userRepository.findById(dummyRequest.getId())).thenReturn(Optional.of(dummyUser));

        UserResponse userResponse = userService.updateUser(dummyRequest);

        verify(userRepository, times(1)).findById(dummyRequest.getId());
        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(dummyUser.getId() , userResponse.getId());
        assertEquals(dummyRequest.getName(), userResponse.getName());
        assertEquals(dummyRequest.getCity(), userResponse.getCity());
        assertEquals(dummyUser.getEmail(), userResponse.getEmail());
        assertEquals(dummyRequest.getAddress(), userResponse.getAddress());
        assertEquals(dummyRequest.getCountry(), userResponse.getCountry());
        assertEquals(dummyRequest.getMobilePhone(), userResponse.getMobilePhone());
        assertEquals(dummyUser.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), userResponse.getDateOfBirth());
    }

    @Test
    void itShouldThrowExceptionAndNotFoundWhenUpdateByRequest() {
        String userId = "1";

        //dummy request
        UserRequest dummyRequest = new UserRequest();
        dummyRequest.setId(userId);
        dummyRequest.setName("test");
        dummyRequest.setAddress("jalanan");
        dummyRequest.setCountry("indo pride");
        dummyRequest.setCity("DIY");
        dummyRequest.setMobilePhone("0898755123");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.updateUser(dummyRequest));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User is not found", exception.getReason());
    }

    @Test
    void itShouldChangePasswordSuccessfulWhenChangePasswordByUserId() {
        String userId = "1";

        //dummy request
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("111");
        changePasswordRequest.setNewPassword("222");
        changePasswordRequest.setConfirmationPassword("222");

        User user = User.builder()
                .userCredential(UserCredential.builder()
                        .password(passwordEncoder.encode(changePasswordRequest.getCurrentPassword()))
                        .build())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getUserCredential().getPassword())).thenReturn(true);

        userService.changePassword(userId, changePasswordRequest);

        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches(changePasswordRequest.getCurrentPassword(), user.getUserCredential().getPassword());
        verify(userCredentialService, times(1)).update(any(UserCredential.class));

        assertNotNull(user);
        assertNotSame(user.getUserCredential().getPassword(), changePasswordRequest.getNewPassword());
        assertNotSame(user.getUserCredential().getPassword(), changePasswordRequest.getConfirmationPassword());
    }

    @Test
    void itShouldThrowExceptionAndUserNotFoundWhenChangePasswordByUserId() {
        String userId = "1";

        //dummy request
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.changePassword(userId, changePasswordRequest));

        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, never()).matches(any(), any());
        verify(userCredentialService, never()).update(any());

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User is not found", exception.getReason());
    }

    @Test
    void itShouldThrowExceptionWhenWrongOldPassword() {
        String userId = "1";

        //dummy request
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("111");
        changePasswordRequest.setNewPassword("222");
        changePasswordRequest.setConfirmationPassword("222");

        User user = User.builder()
                .userCredential(UserCredential.builder()
                        .password(passwordEncoder.encode("wrongPassword"))
                        .build())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getUserCredential().getPassword())).thenReturn(false);

        //exception
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.changePassword(userId, changePasswordRequest));

        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches(changePasswordRequest.getCurrentPassword(), user.getUserCredential().getPassword());
        verify(userCredentialService, never()).update(any());

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Wrong old password", exception.getReason());
    }

    @Test
    void itShouldThrowExceptionWhenNewPasswordNotSameAsConfirmationPassword() {
        String userId = "1";

        //dummy request
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("111");
        changePasswordRequest.setNewPassword("222");
        changePasswordRequest.setConfirmationPassword("333");

        User user = User.builder()
                .userCredential(UserCredential.builder()
                        .password(passwordEncoder.encode(changePasswordRequest.getCurrentPassword()))
                        .build())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getUserCredential().getPassword())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.changePassword(userId, changePasswordRequest));

        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches(changePasswordRequest.getCurrentPassword(), user.getUserCredential().getPassword());
        verify(userCredentialService, never()).update(any());

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("New password and confirmation not same", exception.getReason());
    }

    @Test
    void itShouldDeleteUserSuccessfulWhenDeleteUserById() {
        String userId = "1";

        //dummy user
        User dummyUser = new User();
        dummyUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(dummyUser));

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(dummyUser);

    }

    @Test
    void itShouldThrowExceptionNotFoundWhenDeleteUserById() {
        String userId = "1";

        //dummy user
        User dummyUser = new User();
        dummyUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.deleteUserById(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).delete(dummyUser);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User is not found", exception.getReason());
    }
}
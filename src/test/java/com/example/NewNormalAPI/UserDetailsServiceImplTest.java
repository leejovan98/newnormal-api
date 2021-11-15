package com.example.NewNormalAPI;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.example.NewNormalAPI.user.User;
import com.example.NewNormalAPI.user.UserAlreadyExistsException;
import com.example.NewNormalAPI.user.UserDetailsServiceImpl;
import com.example.NewNormalAPI.user.UserNotVerifiedException;
import com.example.NewNormalAPI.user.UserRepository;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {
    
    @Mock
    private UserRepository users;

    @InjectMocks
    private UserDetailsServiceImpl userSvc;

    @AfterEach
    void tearDown() {
        // clear the database after each test
        users.deleteAll();
    }

    @Test
    void createUser_UserAlreadyExists_ThrowUserAlreadyExistsException() {
        // arrange
        String username = "dummyUser";
        String email = "dummyEmail@gmail.com";

        User user1 = new User();
        user1.setUsername(username);
        user1.setEmail(email);

        User user2 = new User();
        user2.setUsername(username);
        user2.setEmail(email);

        Optional<User> duplicateUser1 = Optional.of(user1);

        // stubbing
        when(users.findByUsername(any(String.class))).thenReturn(duplicateUser1);
        when(users.findByEmail(any(String.class))).thenReturn(duplicateUser1);

        // act + assert
        assertThrows(UserAlreadyExistsException.class, () -> userSvc.createUser(user2));

        // assert
        verify(users).findByUsername(username);
        verify(users).findByEmail(email);
    }

    @Test
    void loadUserEntityByUsername_NotFound_ThrowUsernameNotFoundException() {
        // arrange
        Optional<User> emptyOptional = Optional.empty();
        String username = "dummyUser";
        // stubbing
        when(users.findByUsername(any(String.class))).thenReturn(emptyOptional);
        // act + assert
        assertThrows(UsernameNotFoundException.class, () -> userSvc.loadUserEntityByUsername(username));
        // assert
        verify(users).findByUsername(username);
    }

    @Test
    void loadUserEntityByUsername_NotVerified_ThrowUserNotVerifiedException() {
        // arrange
        User user = new User();
        user.setVerified("N");
        String username = "dummyUser";
        Optional<User> optional = Optional.of(user);
        
        // stubbing
        when(users.findByUsername(any(String.class))).thenReturn(optional);
        // act + assert
        assertThrows(UserNotVerifiedException.class, () -> userSvc.loadUserEntityByUsername(username));
        // assert
        verify(users).findByUsername(username);
    }

    @Test
    void loadUserEntityByUsername_FoundAndVerified_ReturnUser() {
        // arrange
        User user = new User();
        String username = "dummyUser";
        user.setUsername(username);
        user.setVerified("Y");
        
        Optional<User> optional = Optional.of(user);

        // stubbing
        when(users.findByUsername(any(String.class))).thenReturn(optional);
        // act + assert
        User testResult = userSvc.loadUserEntityByUsername(username);
        // assert
        verify(users).findByUsername(username);
        assertEquals(username, testResult.getUsername());
    }
}

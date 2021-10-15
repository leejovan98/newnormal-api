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

        User u1 = new User();
        u1.setUsername(username);
        u1.setEmail(email);

        User u2 = new User();
        u2.setUsername(username);
        u2.setEmail(email);

        Optional<User> duplicateUser1 = Optional.of(u1);

        // stubbing
        when(users.findByUsername(any(String.class))).thenReturn(duplicateUser1);
        when(users.findByEmail(any(String.class))).thenReturn(duplicateUser1);

        // act + assert
        assertThrows(UserAlreadyExistsException.class, () -> userSvc.createUser(u2));

        // assert
        verify(users).findByUsername(username);
        verify(users).findByEmail(email);
    }
}

package com.prem.junit.test.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock UserRepository userRepository;

  @InjectMocks UserService userService;

  @Test
  void testGetUsername() {
    // Arrange
    when(userRepository.findUserById(101)).thenReturn(new User(101, "Prem"));

    // Act
    String result = userService.getUsername(101);

    // Assert
    assertEquals("Prem", result);
  }

  @Test
  void testUserNotFound() {
    when(userRepository.findUserById(999)).thenReturn(null);
    assertEquals("Unknown", userService.getUsername(999));
  }
}

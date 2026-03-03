package com.example.demo;

import com.example.demo.model.UserEntity;
import com.example.demo.model.UserRole;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private final UserService userService = new UserService(userRepository, passwordEncoder);

    @Test
    void registerUser_shouldEncryptPassword() {
        UserEntity user = UserEntity.builder()
                .username("admin")
                .password("plainpass")
                .role(UserRole.ADMIN)
                .build();

        Mockito.when(passwordEncoder.encode("plainpass")).thenReturn("encrypted");
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserEntity saved = userService.registerUser(user);

        assertEquals("encrypted", saved.getPassword());
    }

    @Test
    void authenticate_withValidCredentials_shouldReturnUser() {
        UserEntity user = UserEntity.builder()
                .username("admin")
                .password("encrypted")
                .role(UserRole.ADMIN)
                .build();

        Mockito.when(userRepository.findByUsername("admin")).thenReturn(user);
        Mockito.when(passwordEncoder.matches("plainpass", "encrypted")).thenReturn(true);

        UserEntity authenticated = userService.authenticate("admin", "plainpass");

        assertEquals("admin", authenticated.getUsername());
    }

    @Test
    void authenticate_withInvalidCredentials_shouldThrowException() {
        Mockito.when(userRepository.findByUsername("admin")).thenReturn(null);

        assertThrows(SecurityException.class,
                () -> userService.authenticate("admin", "wrongpass"));
    }
}

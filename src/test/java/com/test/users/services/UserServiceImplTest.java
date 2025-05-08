package com.test.users.services;

import com.test.users.dtos.request.PhoneRequest;
import com.test.users.dtos.request.UserRequest;
import com.test.users.dtos.response.UserResponse;
import com.test.users.models.User;
import com.test.users.repositories.UserRepository;
import com.test.users.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testListAllUsers() {
        // Preparar un usuario de ejemplo
        User user = new User();
        user.setId("b8528c93-830f-4969-a905-820d3f5ec2bb");
        user.setName("Juan Rodriguez");
        user.setEmail("juan.rod@rodriguez.com");
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setLastlogin(new Date());
        user.setIsactive(true);

        // Preparar la respuesta simulada
        Page<User> mockPage = new PageImpl<>(Collections.singletonList(user), PageRequest.of(0, 20, Sort.by("id").ascending()), 1);
        when(userRepo.findFiltering("", PageRequest.of(0, 20, Sort.by("id").ascending()))).thenReturn(mockPage);

        // Llamar al método
        Map<String, Object> result = userService.listAllUsers(0, 20, "id", "asc", "");

        // Verificar la respuesta
        assertNotNull(result);
        //assertTrue(result.containsKey("users"));
        assertTrue(result.containsKey("page"));
        assertTrue(result.containsKey("size"));
        assertTrue(result.containsKey("totalElem"));
        assertTrue(result.containsKey("totalPage"));
    }

    @Test
    void testCreateUser() {
        // Preparar la solicitud
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("john.doe@example.com");
        userRequest.setPassword("Password123*");

        // Agregar teléfonos de ejemplo
        List<PhoneRequest> phoneRequests = new ArrayList<>();
        phoneRequests.add(new PhoneRequest("1234567", "1", "57"));
        phoneRequests.add(new PhoneRequest("654879", "2", "57"));
        userRequest.setPhones(phoneRequests);

        // Preparar la respuesta simulada
        User mockUser = new User();
        mockUser.setName("John Doe");
        mockUser.setEmail("john.doe@example.com");

        when(userRepo.save(any(User.class))).thenReturn(mockUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Llamar al método
        UserResponse result = userService.createUser(userRequest);

        // Verificar la respuesta
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals(2 , result.getPhones().size());
    }
}
package com.projetfulstack.studentgrademanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetfulstack.studentgrademanagement.dto.RegisterRequest;
import com.projetfulstack.studentgrademanagement.entity.UserRole;
import com.projetfulstack.studentgrademanagement.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests pour l'enregistrement d'administrateurs
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
public class AdminRegistrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAdminRegistration_Basic() throws Exception {
        setup();
        
        RegisterRequest adminRequest = new RegisterRequest();
        adminRequest.setFirstName("Admin");
        adminRequest.setLastName("System");
        adminRequest.setEmail("admin@system.com");
        adminRequest.setPassword("AdminPass123#");
        adminRequest.setRole(UserRole.ADMIN);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));
    }

    @Test
    public void testAdminRegistration_Complete() throws Exception {
        setup();
        
        RegisterRequest adminRequest = new RegisterRequest();
        adminRequest.setFirstName("Jean");
        adminRequest.setLastName("Administrateur");
        adminRequest.setEmail("jean.admin@institution.edu");
        adminRequest.setPassword("SecureAdmin2024#");
        adminRequest.setRole(UserRole.ADMIN);
        adminRequest.setAddress("123 Avenue de l'Administration, Yaoundé");
        adminRequest.setPhoneNumber("678901234");
        adminRequest.setDateOfBirth(java.time.LocalDate.of(1980, 1, 15));

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));
    }

    @Test
    public void testAdminRegistration_DuplicateEmail() throws Exception {
        setup();
        
        // Créer un premier administrateur
        RegisterRequest firstAdmin = new RegisterRequest();
        firstAdmin.setFirstName("Admin1");
        firstAdmin.setLastName("System1");
        firstAdmin.setEmail("admin@test.com");
        firstAdmin.setPassword("Password123#");
        firstAdmin.setRole(UserRole.ADMIN);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstAdmin)))
                .andExpect(status().isOk());

        // Tenter de créer un second administrateur avec le même email
        RegisterRequest secondAdmin = new RegisterRequest();
        secondAdmin.setFirstName("Admin2");
        secondAdmin.setLastName("System2");
        secondAdmin.setEmail("admin@test.com");
        secondAdmin.setPassword("Password456#");
        secondAdmin.setRole(UserRole.ADMIN);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondAdmin)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Email is already in use!"));
    }
}

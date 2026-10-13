package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner commandLineRunner(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {


        return args -> {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(()-> roleRepository.save(new Role("ROLE_USER")));

            if(userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User("Admin", "Adminov", "admin@example.com");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("12345678"));
                admin.setRoles(new HashSet<>(Set.of(adminRole)));
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User("User", "Userov", "user@example.com");
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("12345678"));
                user.setRoles(new HashSet<>(Set.of(userRole)));
                userRepository.save(user);
            }
        };
    }
}
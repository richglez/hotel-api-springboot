package com.richglez.hotel.config;

import com.richglez.hotel.common.enums.Roles;
import com.richglez.hotel.users.model.User;
import com.richglez.hotel.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Seeds required startup data. */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createAdminIfNotExists();
    }

    private void createAdminIfNotExists() {
        String adminEmail = "admin@hotel.com";

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = User.builder()
                    .name("Admin")
                    .lastName("Hotel")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("Admin1234!"))
                    .phone("00000000")
                    .role(Roles.ADMIN)
                    .build();

            userRepository.save(admin);
            log.info("Admin created: {} / Admin1234!", adminEmail);
        } else {
            log.info("Admin already exists; skipping creation.");
        }
    }
}

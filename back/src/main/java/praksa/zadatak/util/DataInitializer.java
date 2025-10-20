package praksa.zadatak.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import praksa.zadatak.enums.UserRole;
import praksa.zadatak.model.Admin;
import praksa.zadatak.repository.AdminRepository;
import praksa.zadatak.repository.BaseUserRepository;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataInitializer implements ApplicationRunner {
    private final AdminRepository adminRepository;
    private final BaseUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.existsByUsername("admin")) { return; }
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(UserRole.ROLE_ADMIN);
        adminRepository.save(admin);
    }
}

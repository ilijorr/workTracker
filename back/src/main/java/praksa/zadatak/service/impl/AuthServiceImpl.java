package praksa.zadatak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.LoginRequestDTO;
import praksa.zadatak.dto.LoginResponseDTO;
import praksa.zadatak.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    public LoginResponseDTO login(LoginRequestDTO request) {
        return null;
    }
}

package praksa.zadatak.service;

import praksa.zadatak.dto.LoginRequestDTO;
import praksa.zadatak.dto.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
}

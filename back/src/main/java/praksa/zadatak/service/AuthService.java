package praksa.zadatak.service;

import praksa.zadatak.dto.request.LoginRequestDTO;
import praksa.zadatak.dto.response.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
}

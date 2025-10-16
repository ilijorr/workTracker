package praksa.zadatak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.LoginRequestDTO;
import praksa.zadatak.dto.LoginResponseDTO;
import praksa.zadatak.model.BaseUser;
import praksa.zadatak.service.AuthService;
import praksa.zadatak.service.BaseUserService;
import praksa.zadatak.util.JwtUtil;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final BaseUserService userService;
    private final JwtUtil jwtUtil;

    public LoginResponseDTO login(LoginRequestDTO request) {
        BaseUser user = userService.get(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Username or password are invalid"));
        if (!user.getPassword().equals(request.getPassword())) {
            throw new BadCredentialsException("Username or password are invalid");
        }

        return new LoginResponseDTO(
                jwtUtil.generateJwt(user)
        );
    }
}

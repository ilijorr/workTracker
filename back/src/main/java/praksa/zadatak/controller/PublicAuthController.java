package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import praksa.zadatak.dto.LoginRequestDTO;
import praksa.zadatak.dto.LoginResponseDTO;
import praksa.zadatak.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/auth")
public class PublicAuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request
            ) {
        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}

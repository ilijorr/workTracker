package praksa.zadatak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.model.BaseUser;
import praksa.zadatak.repository.BaseUserRepository;
import praksa.zadatak.service.BaseUserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseUserServiceImpl implements BaseUserService {
    private final BaseUserRepository userRepository;

    public BaseUser get(Long id) {
        return null;
    }

    public Optional<BaseUser> get(String username) {
        return userRepository.findByUsername(username);
    }
}

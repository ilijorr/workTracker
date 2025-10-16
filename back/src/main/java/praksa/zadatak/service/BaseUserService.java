package praksa.zadatak.service;

import praksa.zadatak.model.BaseUser;

import java.util.Optional;

public interface BaseUserService {
    BaseUser get(Long id);
    Optional<BaseUser> get(String username);
}

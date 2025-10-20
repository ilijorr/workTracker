package praksa.zadatak.service;

import praksa.zadatak.model.BaseUser;

public interface BaseUserService {
    BaseUser get(Long id);
    BaseUser get(String username);
    Boolean existsByUsername(String username);
}

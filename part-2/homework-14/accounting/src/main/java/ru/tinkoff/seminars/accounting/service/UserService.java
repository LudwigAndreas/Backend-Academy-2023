package ru.tinkoff.seminars.accounting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import ru.tinkoff.seminars.accounting.dto.user.UserDto;
import ru.tinkoff.seminars.accounting.dto.user.UserRegistrationDto;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<UserDto> findById(UUID id);

    Optional<UserDto> findByLogin(String login);

    UserDto save(UserRegistrationDto user);

    void delete(UUID id);

    Optional<UserDto> patch(UUID id, JsonPatch patch) throws JsonPatchException, JsonProcessingException;

    Optional<UserDto> patch(UUID id, UserDto patch) throws JsonMappingException;

    Optional<UserDto> update(UUID id, UserDto user) throws JsonMappingException;

}

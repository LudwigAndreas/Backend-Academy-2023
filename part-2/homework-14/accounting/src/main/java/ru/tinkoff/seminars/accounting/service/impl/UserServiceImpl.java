package ru.tinkoff.seminars.accounting.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.stereotype.Service;
import ru.tinkoff.seminars.accounting.dto.user.UserDto;
import ru.tinkoff.seminars.accounting.dto.user.UserRegistrationDto;
import ru.tinkoff.seminars.accounting.mapper.UserModelDtoMapper;
import ru.tinkoff.seminars.accounting.model.UserModel;
import ru.tinkoff.seminars.accounting.repository.UserRepository;
import ru.tinkoff.seminars.accounting.service.UserService;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserModelDtoMapper userModelDtoMapper;

    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository, UserModelDtoMapper userModelDtoMapper, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.userModelDtoMapper = userModelDtoMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<UserDto> findById(UUID id) {
        return userRepository.findById(id).map(userModelDtoMapper::toDto);
    }

    @Override
    public Optional<UserDto> findByLogin(String login) {
        return userRepository.findByLogin(login).map(userModelDtoMapper::toDto);
    }

    @Override
    public UserDto save(UserRegistrationDto user) {
        UserModel userModel = userModelDtoMapper.toModel(user);
        return userModelDtoMapper.toDto(userRepository.save(userModel));
    }

    @Override
    public void delete(UUID id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    @Override
    public Optional<UserDto> patch(UUID id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        UserModel userModel = userRepository.findById(id).orElseThrow();
        UserModel patchedUserModel = applyPatch(userModel, patch);
        return Optional.of(userModelDtoMapper.toDto(
                userRepository.save(applyPatch(userModel, userModelDtoMapper.toDto(patchedUserModel)))
        ));
    }

    @Override
    public Optional<UserDto> patch(UUID id, UserDto patch) throws JsonMappingException {
        UserModel userModel = userRepository.findById(id).orElseThrow();
        return Optional.of(userModelDtoMapper.toDto(userRepository.save(applyPatch(userModel, patch))));
    }

    @Override
    public Optional<UserDto> update(UUID id, UserDto user) throws JsonMappingException {
        UserModel userModel = userRepository.findById(id).orElseThrow();
        objectMapper.updateValue(userModel, user);
        return Optional.of(userModelDtoMapper.toDto(userRepository.save(userModel)));
    }

    private UserModel applyPatch(UserModel userModel, UserDto patch) throws JsonMappingException {
        objectMapper.updateValue(userModel, patch);
        return userModel;
    }

    private UserModel applyPatch(UserModel userModel, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(userModel, JsonNode.class));
        return objectMapper.treeToValue(patched, UserModel.class);
    }
}

package ru.tinkoff.seminars.accounting.controller;

import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.seminars.accounting.dto.user.UserDto;
import ru.tinkoff.seminars.accounting.dto.user.UserRegistrationDto;
import ru.tinkoff.seminars.accounting.service.OAuth2IntegrationUserService;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final OAuth2IntegrationUserService usersService;

    public UsersController(OAuth2IntegrationUserService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/me",
            produces = { "application/json" }
    )
//    TODO: add @PreAuthorize("hasRole('ROLE_USER')") or @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> usersMeGet(@AuthenticationPrincipal Jwt jwt) {
        Optional<UserDto> user = usersService.findById(UUID.fromString(jwt.getClaim("user_id").toString()));
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{userId}",
            produces = { "application/json" }
    )
    public ResponseEntity<UserDto> usersUserIdGet(@PathVariable UUID userId) {
        Optional<UserDto> user = usersService.findById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<UserDto> usersPost(@Valid @RequestBody UserRegistrationDto userPost) {
        UserDto user = usersService.addUser(userPost);
        return ResponseEntity.ok(user);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{userId}",
            produces = { "application/json" }
    )
    public ResponseEntity<Void> usersUserIdDelete(@PathVariable UUID userId,
                                                  @AuthenticationPrincipal Jwt jwt) {
        usersService.deleteUser(jwt, userId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/{userId}",
            produces = { "application/json" },
            consumes = { "application/json-patch+json" }
    )
    //    TODO: remove throws JsonPatchException, JsonProcessingException
    public ResponseEntity<UserDto> usersUserIdPatch(@PathVariable UUID userId,
                                                 @Valid @RequestBody JsonPatch jsonPatch,
                                                 @AuthenticationPrincipal Jwt jwt) {
        Optional<UserDto> user = usersService.patchUser(jwt, userId, jsonPatch);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/{userId}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<UserDto> usersUserIdPatch(@PathVariable UUID userId,
                                                 @Valid @RequestBody UserDto userPatch,
                                                 @AuthenticationPrincipal Jwt jwt) {
        Optional<UserDto> user = usersService.patchUser(jwt, userId, userPatch);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{userId}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<UserDto> usersUserIdPut(@PathVariable UUID userId,
                                               @Valid @RequestBody UserDto userUpdate,
                                               @AuthenticationPrincipal Jwt jwt) {
        Optional<UserDto> user = usersService.updateUser(jwt, userId, userUpdate);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/verification-link",
            produces = { "application/json" }
    )
    @PreAuthorize("isAuthenticated()")
//    TODO: add @PreAuthorize("hasRole('ROLE_USER')") or @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> usersVerificationLinkUserIdGet(@AuthenticationPrincipal Jwt jwt) {
        usersService.sendVerificationEmail(jwt);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/reset-password",
            produces = { "application/json" }
    )
    @PreAuthorize("isAuthenticated()")
//    TODO: add @PreAuthorize("hasRole('ROLE_USER')") or @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> usersResetPasswordUserIdGet(@AuthenticationPrincipal Jwt jwt) {
        usersService.sendPasswordResetEmail(jwt);
        return ResponseEntity.ok().build();
    }
}

package ru.tinkoff.seminars.accounting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal Jwt jwt) {
        logger.info("Hello, " + jwt.getClaimAsString("preferred_username"));
        return "Hello, " + jwt.getClaimAsString("preferred_username");
    }

    @GetMapping("/me")
    public Authentication me() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }
}

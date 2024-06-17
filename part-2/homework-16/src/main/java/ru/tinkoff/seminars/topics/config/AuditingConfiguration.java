package ru.tinkoff.seminars.topics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AuditingConfiguration {

    @Bean
    public AuditorAware<UUID> getCurrentAuditor() {
        return new JwtAuditorAware();
    }

    static class JwtAuditorAware implements AuditorAware<UUID> {
        @Override
        public Optional<UUID> getCurrentAuditor() {
            return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                    .filter(authentication -> authentication instanceof JwtAuthenticationToken)
                    .map(authentication -> ((JwtAuthenticationToken) authentication).getToken().getClaim("sub"));
        }
    }
}

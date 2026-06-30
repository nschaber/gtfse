package de.nsc.gtfseapi.adapter.utils;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

import static org.springframework.http.HttpMethod.GET;

public class SecurityUtils {

    public static final String REALM_ACCESS = "realm_access";
    public static final String ROLE = "ROLE_";
    public static final String ROLES = "roles";
    public static final String GROUPS = "groups";

    public static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> getMatcher() {
        return (final var authorize) -> authorize
                .requestMatchers(GET, "/swagger-ui.html").permitAll()
                .requestMatchers(GET, "/swagger-ui/**").permitAll()
                .requestMatchers(GET, "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated();
    }

    public static Customizer<CorsConfigurer<HttpSecurity>> getCorsCustomizer() {
        return (final var cors) -> {
            final var configuration = new CorsConfiguration();
            final var source = new UrlBasedCorsConfigurationSource();
            configuration.setAllowedOriginPatterns(List.of("*"));
            configuration.setAllowedMethods(List.of("*"));
            configuration.setAllowedHeaders(List.of("*"));
            configuration.setAllowCredentials(true);
            source.registerCorsConfiguration("/**", configuration);
            cors.configurationSource(source);
        };
    }

    public static Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> getResourceServerCustomizer() {
        return (final var oauth2) -> oauth2.jwt((final var jwt) -> jwt.jwtAuthenticationConverter((final var converter) -> {
            final var grantedAuthorities = new ArrayList<GrantedAuthority>();
            if (converter.hasClaim(REALM_ACCESS)) {
                final Map<String, Collection<String>> realmAccess = converter.getClaim(REALM_ACCESS);
                final Collection<String> roles;
                if (Objects.nonNull(realmAccess)) {
                    roles = realmAccess.get(ROLES);
                    roles.stream()
                            .map((final var role) -> new SimpleGrantedAuthority(ROLE + role))
                            .forEach(grantedAuthorities::add);
                }
            }
            if (converter.hasClaim(GROUPS)) {
                final Collection<String> groups = converter.getClaim(GROUPS);
                if (Objects.nonNull(groups)) {
                    groups.stream()
                            .map((final var role) -> new SimpleGrantedAuthority(ROLE + role.replace("/", "")))
                            .forEach(grantedAuthorities::add);
                }
            }
            return new JwtAuthenticationToken(converter, grantedAuthorities);
        }));
    }

}


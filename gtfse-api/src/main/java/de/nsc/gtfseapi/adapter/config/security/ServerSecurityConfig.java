package de.nsc.gtfseapi.adapter.config.security;

import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import static de.nsc.gtfseapi.adapter.utils.SecurityUtils.*;


@Configuration
@EnableWebSecurity
public class ServerSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(@NonNull final JwtDecoder jwtDecoder) {
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
        return new ProviderManager(jwtAuthenticationProvider);
    }

    @Bean
    @Profile(value = {"prod"})
    public SecurityFilterChain prodFilterChain(@NonNull final HttpSecurity http) {
        http
                .cors(getCorsCustomizer())
                .authorizeHttpRequests(getMatcher())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(getResourceServerCustomizer());
        return http.build();
    }

    @Bean
    @Profile(value = {"dev", "test"})
    public SecurityFilterChain devFilterChain(@NonNull final HttpSecurity http) {
        http
                .cors(getCorsCustomizer())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((final var authorize) -> authorize.anyRequest().permitAll())
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(getResourceServerCustomizer());
        return http.build();
    }

}
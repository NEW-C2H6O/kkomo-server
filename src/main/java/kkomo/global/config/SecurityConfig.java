package kkomo.global.config;

import kkomo.security.service.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Oauth2UserService oauth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-resources",
                    "/actuator/**",
                    "/test/signup",
                    "/",
                    "/ws/**",
                    "/auth/login/**",
                    "/oauth2/**",
                    "/reservations/**"
                )
                .permitAll()
                .requestMatchers(
                    "/ott/**"
                )
                .hasRole("ACTIVATED")
                .requestMatchers(
                    "/admin/**"
                )
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .failureUrl("/login?error")
                .redirectionEndpoint(redirection -> redirection
                    .baseUri("/login/oauth2/code/kakao"))
                .userInfoEndpoint(userInfoEndpoint ->
                    userInfoEndpoint.userService(oauth2UserService)
                )
            )
            .sessionManagement(sessionManagement -> sessionManagement
                .sessionFixation().newSession()
                .maximumSessions(1)
                .expiredUrl("/login?expired"));
        return http.build();
    }
}

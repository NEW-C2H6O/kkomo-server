package kkomo.global.config;

import kkomo.auth.CustomAuthenticationEntryPoint;
import kkomo.auth.handler.OAuth2FailureHandler;
import kkomo.auth.handler.OAuth2SuccessHandler;
import kkomo.auth.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-resources",
                    "/actuator/**",
                    "/",
                    "/ws/**",
                    "/oauth2/**",
                    "/auth/**",
                    "/login"
                )
                .permitAll()
                .requestMatchers(
                    "/ott/**",
                    "/reservations/**"
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
                .redirectionEndpoint(redirection -> redirection
                    .baseUri("/login/oauth2/code/kakao"))
                .userInfoEndpoint(userInfoEndpoint ->
                    userInfoEndpoint.userService(oAuth2UserService)
                )
                .loginProcessingUrl("/auth/login")
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2FailureHandler)
            )
            .exceptionHandling(httpSecurityExceptionHandling ->
                    httpSecurityExceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint))
            .sessionManagement(sessionManagement -> sessionManagement
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry())
                .expiredUrl("/login?expired"));
        return http.build();
    }
}

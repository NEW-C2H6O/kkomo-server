package kkomo.global.config;

import kkomo.auth.AuthorizationRequestRedirectResolver;
import kkomo.auth.ResponseEntityAuthenticationEntryPoint;
import kkomo.auth.handler.OAuth2LogoutSuccessHandler;
import kkomo.auth.handler.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DefaultOAuth2UserService userService;
    private final OAuth2LoginSuccessHandler loginSuccessHandler;
    private final OAuth2LogoutSuccessHandler logoutSuccessHandler;
    private final AuthorizationRequestRedirectResolver authorizationRequestRedirectResolver;
    private final ResponseEntityAuthenticationEntryPoint authenticationEntryPoint;

    public static final List<String> clients = List.of(
        "http://localhost:3000",
        "https://kkomo.site",
        "https://new-c2h6o.github.io",
        "https://api.kkomo.site"
    );

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
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
                .hasAnyRole("ACTIVATED", "ADMIN")
                .requestMatchers(
                    "/admin/**",
                    "/admin-page"
                )
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
            )
            .exceptionHandling(exceptionHandling ->
                exceptionHandling
                    .accessDeniedPage("/access-denied")
            )
            .oauth2Login(oauth2 -> oauth2
                .redirectionEndpoint(redirection -> redirection
                    .baseUri("/login/oauth2/code/kakao"))
                .userInfoEndpoint(userInfoEndpoint ->
                    userInfoEndpoint.userService(userService)
                )
                .loginProcessingUrl("/auth/login")
                .authorizationEndpoint(authorization ->
                    authorization.authorizationRequestResolver(authorizationRequestRedirectResolver)
                )
                .successHandler(loginSuccessHandler)
            )
            .exceptionHandling(httpSecurityExceptionHandling ->
                    httpSecurityExceptionHandling.authenticationEntryPoint(authenticationEntryPoint))
            .sessionManagement(sessionManagement -> sessionManagement
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry()))
            .logout(logout -> logout
                .logoutRequestMatcher(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/auth/logout"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessHandler(logoutSuccessHandler)
            );
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(clients);
        configuration.setAllowedMethods(Arrays.asList(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()
        ));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

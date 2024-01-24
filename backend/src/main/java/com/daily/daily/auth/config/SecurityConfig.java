package com.daily.daily.auth.config;

import com.daily.daily.auth.jwt.JwtAuthorizationFilter;
import com.daily.daily.oauth.handler.OAuth2FailureHandler;
import com.daily.daily.oauth.handler.OAuth2SuccessHandler;
import com.daily.daily.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final CustomOAuth2UserService principalOauth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource));
        http.csrf(CsrfConfigurer::disable);
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(registry -> registry
                .anyRequest().permitAll()
        );
        http.exceptionHandling(configurer -> configurer
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
//                .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("https://localhost:3000/login"))
        );
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.oauth2Login(configurer -> configurer
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2FailureHandler)
                .userInfoEndpoint(endpointConfig -> endpointConfig
                        .userService(principalOauth2UserService))
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

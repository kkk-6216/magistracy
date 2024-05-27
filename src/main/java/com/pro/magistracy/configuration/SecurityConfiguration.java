package com.pro.magistracy.configuration;

import com.pro.magistracy.service.UserService;
import com.pro.magistracy.settings.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.Set;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final UserService userService;
    private final SettingsService settingsService;

    @Autowired
    public SecurityConfiguration(UserService userService, SettingsService settingsService) {
        this.userService = userService;
        this.settingsService = settingsService;
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> authorizationManager() {
        return (authorizationContext, object) -> new AuthorizationDecision(settingsService.getSettings().isFormRegister());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/images/**", "/assets/**", "/data/**", "/admin/fonts/**", "/js/**", "/css/**", "/webjars/**").permitAll()
                                .requestMatchers("/sign-in", "/sign-in?error").permitAll()
                                .requestMatchers("/sign-up").access(authorizationManager())
                                .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                                .requestMatchers("/rector", "/rector/**").hasRole("RECTOR")
                                .requestMatchers("/teachers", "/teachers/**").hasRole("TEACHER")
                                .requestMatchers("/", "/profile", "/documents", "/exams", "/images", "/notifications").hasRole("STUDENT")
                                .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginPage("/sign-in")
                        .loginProcessingUrl("/sign-in-processing")
                        .successHandler((request, response, authentication) -> {
                            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                            if (roles.contains("ROLE_ADMIN")) {
                                response.sendRedirect("/admin");
                            } else if (roles.contains("ROLE_TEACHER")) {
                                response.sendRedirect("/teachers");
                            } else if (roles.contains("ROLE_RECTOR")) {
                                response.sendRedirect("/rector");
                            } else if (roles.contains("ROLE_STUDENT")) {
                                response.sendRedirect("/");
                            } else {
                                throw new IllegalStateException("Unexpected role: " + roles);
                            }
                        })
                        .failureForwardUrl("/sign-in?error").permitAll()
                ).logout(logout -> logout
                        .logoutUrl("/sign-out")
                        .logoutSuccessUrl("/sign-in")
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


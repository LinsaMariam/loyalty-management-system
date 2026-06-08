package com.springboot.loyaltymanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        //  Everything else under /api/ needs Basic Auth
                        .requestMatchers(HttpMethod.GET, "/api/admin/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/admin").authenticated()
                        //  Keep admin.html for admins secured
                        .requestMatchers(HttpMethod.GET, "/admin.html").authenticated()
                        //  Everything else is open
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults()); // enable basic auth

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(BCryptPasswordEncoder encoder) {
        UserDetails user = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

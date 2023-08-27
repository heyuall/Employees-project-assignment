package com.johns_group_hr_system.project_employees_assignments.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("Houssem")
                .password(passwordEncoder().encode("psd"))
                .roles("PROJECT_MANAGER")
                .build();
        UserDetails user2 = User.withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("DEVELOPER")
                .build();
        UserDetails user3 = User.withUsername("hr")
                .password(passwordEncoder().encode("hr"))
                .roles("HR")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/assignments/**").hasAnyRole("PROJECT_MANAGER", "HR")
                        .requestMatchers("/projects/**").hasRole("PROJECT_MANAGER")
                        .requestMatchers("/employees/list").hasAnyRole("DEVELOPER", "PROJECT_MANAGER", "HR")
                        .requestMatchers("/employees/**").hasRole("HR")
                        .requestMatchers("/api-docs").permitAll()
                        .anyRequest().authenticated()
//                        .requestMatchers("/swagger-ui/index.html").permitAll()
                )
                .httpBasic(withDefaults());
        return http.build();
    }
}

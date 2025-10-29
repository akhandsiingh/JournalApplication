package com.example.AkSB.config;

// Removed: import com.example.AkSB.service.UserDetailsServiceImpl;
// Removed: import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
// Removed: import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    // Removed the @Autowired UserDetailsServiceImpl field to break the cycle.
    // Spring will find the UserDetailsServiceImpl bean (due to @Component) automatically.

    // IN YOUR SpringSecurity.java CONFIGURATION:
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        // 1. PLACE THE SPECIFIC PERMIT ALL RULE FIRST
                        .requestMatchers(HttpMethod.POST, "/user").permitAll()

                        // 2. Add other specific rules
                        .requestMatchers("/public/**").permitAll()

                        // 3. Keep the general authenticated rules for remaining URLs
                        .requestMatchers("/journal/**", "/user/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 4. Fallback: Catch anything else and require authentication
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
    // Removed the problematic configureGlobal method.

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
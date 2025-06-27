package it.epicode.bwfinalboss.security;

import it.epicode.bwfinalboss.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.formLogin(http->http.disable());


        httpSecurity.csrf(http->http.disable());

        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        httpSecurity.cors(Customizer.withDefaults());

        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/clienti/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/indirizzi/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/api/utenti/*/avatar").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .requestMatchers(HttpMethod.PATCH, "/api/utenti/*/avatar").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/**").hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/api/utenti/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/**").hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/**").hasRole(Role.ADMIN.name())
                .anyRequest().denyAll()
        );
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){


        return new BCryptPasswordEncoder(15);
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
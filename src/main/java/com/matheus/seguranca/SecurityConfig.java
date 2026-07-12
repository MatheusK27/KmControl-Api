package com.matheus.seguranca;


import com.matheus.infra.TratadorErro403;
import com.matheus.infra.TratadorErro401;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    private final TratadorErro401 tratadorErro401;
    private final TratadorErro403 tratadorErro403;


    @Bean
    public SecurityFilterChain filtroSeguranca(HttpSecurity http) throws Exception {
              return http.csrf(AbstractHttpConfigurer::disable)
                      .cors(Customizer.withDefaults())
                      .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                      .authorizeHttpRequests(auth->auth
                              .requestMatchers(HttpMethod.POST, "/usuario").permitAll()
                              .requestMatchers(HttpMethod.POST, "/login").permitAll()
                              .requestMatchers(HttpMethod.GET, "/kmcontrol.html").permitAll()
                              .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                              .requestMatchers("/swagger-ui/**",
                                      "/v3/api-docs/**",
                                      "/swagger-ui.html").permitAll()
                              .anyRequest().authenticated())
                      .exceptionHandling(ex-> ex.authenticationEntryPoint(tratadorErro401)
                              .accessDeniedHandler(tratadorErro403))
                      .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                      .build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/kmcontrol.html");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

package com.example.EmployeeManager.config;

import com.example.EmployeeManager.security.JwtAuthenticationEntryPoint;
import com.example.EmployeeManager.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

//@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("securityFilterChain called");
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests()
                .requestMatchers("/auth/login").permitAll()
                //.requestMatchers("/auth/register").permitAll()
                // Configure access to specific URL patterns based on roles
                //.requestMatchers("/employee/**").hasAnyRole("USER", "ADMIN", "HR")
                .requestMatchers(HttpMethod.POST, "/employee").hasRole("HR") // Restrict POST to HR
                .requestMatchers("/employee/admin/**").hasAnyRole("ADMIN", "HR")
                .requestMatchers("/employee/update/**").hasRole("USER")
                .requestMatchers("/employee/admin/update/**").hasAnyRole("ADMIN", "HR")
                //.requestMatchers(HttpMethod.DELETE,"/employee/{employeeId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/employee/{employeeId}").hasAnyRole("USER", "ADMIN","HR")
                .requestMatchers("/employee/admin/{employeeId}").hasAnyRole("ADMIN","HR")
                .requestMatchers("/employee/admin/search").hasAnyRole("HR", "ADMIN")
                .requestMatchers("/employee/job/**").hasAnyRole("HR", "ADMIN")
                .requestMatchers("/employee/{employeeId}/salary").hasRole("HR")
                .requestMatchers("/employee/statistics").hasRole("HR")
                .requestMatchers("/employee/statistics").hasRole("HR")

                .anyRequest().fullyAuthenticated()
                .and()
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}

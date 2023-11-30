package com.example.EmployeeManager.config;

import com.example.EmployeeManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration

public class AppConfig extends InMemoryUserDetailsManager   {
    @Autowired
    UserRepository userRepository;
//    @Bean
//    UserDetailsService userDetailsService()
//    {
//        UserDetails user1 = User.builder().username("Sourav").password(passwordEncoder().encode("abcd")).roles("ADMIN").build();
//        UserDetails user2 = User.builder().username("Gaurav").password(passwordEncoder().encode("abcd")).roles("HR").build();
  //   UserDetails user3 = User.builder().username("Neha").password(passwordEncoder().encode("abcd")).roles("USER").build();
//
//
//        return new InMemoryUserDetailsManager(user1,user2,user3);
//    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //UserDetails user = this.users.get(username.toLowerCase());
        System.out.println("loadUserByUsername called");
        com.example.EmployeeManager.entity.User user = userRepository.findByUsernameIgnoreCase(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        System.out.println(user.getAuthorities());
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
               user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());

    }
}

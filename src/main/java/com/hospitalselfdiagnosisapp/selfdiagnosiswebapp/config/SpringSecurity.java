package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.config;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers("/", "/login","/register","/register/save","/CSS","/resources/**", "static/assets/**").permitAll()
                                .requestMatchers("/home").hasAuthority("ROLE_PATIENT")
                                .requestMatchers("/resources/","/assets","/static").permitAll()
//                                .requestMatchers("/pharmacy/**").hasRole("ROLE_PHARMACY")
                                .requestMatchers("/patients").hasAnyAuthority("ADMIN","ROLE_PATIENT")
                                .anyRequest().authenticated()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/home")
//                                .defaultSuccessUrl("/patients")
                                .failureUrl("/login?error")

                                .permitAll()

                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                ).exceptionHandling()
                .accessDeniedPage("/accessDenied");
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


}

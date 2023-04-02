package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    private final UserDetailsService userDetailsService;
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public SpringSecurity(UserDetailsService userDetailsService, AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((requests) ->
                                requests.requestMatchers("/", "/login", "/register", "/register/save", "/css/**", "/resources/**", "static/**", "/assets/**", "/diagnosis").permitAll()
                                        .requestMatchers("/home").hasAnyAuthority("ROLE_PATIENT", "ADMIN")
                                        .requestMatchers("/resources/", "/assets", "/static", "getSymptoms", "/doctor/register").permitAll()
                                        //.requestMatchers("/pharmacy/**").hasRole("admin")
                                        .requestMatchers("/patients").hasAuthority("ADMIN")
                                        .requestMatchers("/pharmacy").hasAuthority("ADMIN")
                                        .requestMatchers("/ddashboard").hasAnyAuthority("ROLE_DOCTOR", "ADMIN")
                                        .requestMatchers("/dashboard").hasAnyAuthority("ROLE_PATIENT", "ADMIN")
                                        .requestMatchers("/pdashboard").hasAnyAuthority("ROLE_PHARMACY", "ADMIN")
                                        .requestMatchers("/admindashboard").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                                      //  .anyRequest().permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler(authenticationSuccessHandler)
//
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

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler();
    }

    public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            if (roles.contains("ROLE_PATIENT")) {
                setDefaultTargetUrl("/dashboard");
            } else if (roles.contains("ROLE_DOCTOR")) {
                setDefaultTargetUrl("/ddashboard");
            } else if (roles.contains("ROLE_PHARMACY")) {
                setDefaultTargetUrl("/pdashboard");
            } else if (roles.contains("ADMIN")) {
                setDefaultTargetUrl("/adminpanel");
            } else {
                setDefaultTargetUrl("/");
            }
            super.onAuthenticationSuccess(request, response, authentication);
        }


    }
}
package com.dev.cinema.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/movies/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET,"/movies/**").permitAll()
                .antMatchers(HttpMethod.POST,"/cinema-halls/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET,"/cinema-halls/**").permitAll()
                .antMatchers(HttpMethod.POST,"/movie-session/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET,"/movie-session/available**").permitAll()
                .antMatchers(HttpMethod.POST,"/orders/**").hasRole(USER)
                .antMatchers(HttpMethod.GET,"/orders/**").hasRole(USER)
                .antMatchers(HttpMethod.GET,"/users/by-email/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST,"/shopping-carts/movie-sessions/**").hasRole(USER)
                .antMatchers(HttpMethod.GET,"/shopping-carts/by-user/**").permitAll()
                .antMatchers(HttpMethod.POST,"/registration").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}

package com.chamberos.chamberosapi.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import com.chamberos.chamberosapi.infrastructure.security.jwt.AuthEntryPointJwt;
import com.chamberos.chamberosapi.infrastructure.security.jwt.filters.AuthTokenFilter;
import com.chamberos.chamberosapi.infrastructure.security.services.UserDetailsServiceImpl;
import com.chamberos.chamberosapi.utils.AppConstants;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Autowired
  UserDetailsServiceImpl userDetailsServiceImpl;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors().and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests().antMatchers(AppConstants.ROOT_PATH + "profession/get-all/**").permitAll().and()
        .authorizeRequests().antMatchers(AppConstants.AUTH_PATH + "/**").permitAll().and()
        .authorizeRequests().antMatchers(AppConstants.ROOT_PATH + "docs/**").permitAll()
        .antMatchers("/api/test/**").permitAll()
        .anyRequest()
        .authenticated();

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsServiceImpl);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }
}

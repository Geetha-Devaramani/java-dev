package com.ring.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import javax.annotation.Resource;

@EnableResourceServer
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE - 1)

/**In Spring Boot 2, if we want our own security configuration, we can simply add a custom WebSecurityConfigurerAdapter. This will disable the default auto-configuration and enable our custom security configuration**/
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Resource(
      name = "loginService")
  UserDetailsService customUserDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        .antMatchers("/app/login").permitAll()
        .anyRequest().authenticated();

  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth
        .parentAuthenticationManager(authenticationManager)
        .userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
        .ignoring()
        .antMatchers("/app/recover-account", "/app/reset-credentials", "/users",
            "/app/social-login", "/app/clients", "/app/screen");

  }

}

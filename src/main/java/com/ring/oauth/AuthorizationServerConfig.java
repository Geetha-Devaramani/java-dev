package com.ring.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthorizationServerConfig
    extends
      AuthorizationServerConfigurerAdapter {
  
//  
//  static final String CLIEN_ID = "devglan-client";
//  static final String CLIENT_SECRET = "devglan-secret";
//  static final String GRANT_TYPE = "password";
//  static final String AUTHORIZATION_CODE = "authorization_code";
//  static final String REFRESH_TOKEN = "refresh_token";
//  static final String IMPLICIT = "implicit";
//  static final String SCOPE_READ = "read";
//  static final String SCOPE_WRITE = "write";
//  static final String TRUST = "trust";
//  static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
//    static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;

  
  
  
  @Autowired
  private AuthenticationManager authenticationManager;

  @Resource(
      name = "loginService")
  UserDetailsService customUserDetailsService;

  @Autowired
  private TokenEnhancer customTokenEnhancer;

  @Autowired
  private DataSource dataSource;

  @Bean
  public TokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }

  @Bean
  public JdbcClientDetailsService clientDetailsService() {
    return new JdbcClientDetailsService(dataSource);
  }
//defines the security constarints on the token endpoint.
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security)
      throws Exception {

    security.tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()");
  }

  //clients details can be configured
  @Override
  public void configure(ClientDetailsServiceConfigurer clients)
      throws Exception {
    clients.withClientDetails(clientDetailsService());
  }
  
//  @Override
//  public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
//
//    configurer
//        .inMemory()
//        .withClient(CLIEN_ID)
//        .secret(CLIENT_SECRET)
//        .authorizedGrantTypes(GRANT_TYPE, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT )
//        .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
//        .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS).
//        refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
//  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints)
      throws Exception {

    endpoints
        .tokenEnhancer(customTokenEnhancer)
        .tokenStore(tokenStore())
        .reuseRefreshTokens(true)
        .authenticationManager(authenticationManager)
        .userDetailsService(customUserDetailsService)
        .pathMapping("/oauth/token", "/app/login");
  }

}

package com.pingidentity.oidclogin;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               // home, registration and static content is unprotected
               .mvcMatchers("/", "/register", "/register-pairing-wait", "/css/**", "/img/**", "/vendor/**").permitAll()
               // everything else must be authenticated
               .anyRequest().authenticated()
           .and().oauth2Login().loginPage("/oauth2/authorization/oidc-app");
    }

}

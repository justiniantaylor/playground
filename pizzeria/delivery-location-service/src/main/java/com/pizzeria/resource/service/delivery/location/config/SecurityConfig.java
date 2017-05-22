package com.pizzeria.resource.service.delivery.location.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// We need this to prevent the browser from popping up a dialog on a 401
		http.httpBasic().disable().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());		
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/**").hasRole("USER").anyRequest().authenticated();
	}
}
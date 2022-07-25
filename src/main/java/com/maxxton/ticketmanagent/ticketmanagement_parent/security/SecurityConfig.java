package com.maxxton.ticketmanagent.ticketmanagement_parent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import com.maxxton.ticketmanagent.ticketmanagement_parent.service.SingleUserDetailsService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SingleUserDetailsService singleUserDetailsService;
	
	@Bean
	public AuthenticationProvider authProvider() {

		DaoAuthenticationProvider daprovider = new DaoAuthenticationProvider();
		daprovider.setUserDetailsService(singleUserDetailsService);
		daprovider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return daprovider;

	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/console/**").permitAll()
                .antMatchers("/employee/**", "/user/**", "/ticket/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.headers().frameOptions().sameOrigin();
	}
}
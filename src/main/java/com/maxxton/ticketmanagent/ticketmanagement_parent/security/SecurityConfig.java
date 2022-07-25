package com.maxxton.ticketmanagent.ticketmanagement_parent.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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
	
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		List<UserDetails> user = new ArrayList<>();
		user.add(User.withDefaultPasswordEncoder().username("admin").password("admin").roles("USERS").build());
		return new InMemoryUserDetailsManager(user);
	}
}
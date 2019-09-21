package com.auzmor.sample.sms.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class AuzmorBasicAuthConfigurerAdapter extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.authoritiesByUsernameQuery("SELECT USERNAME, 'ROLE_USER' as ROLE from account where USERNAME=?")
				.usersByUsernameQuery("SELECT username as USERNAME, concat('{noop}',auth_id) as PASSWORD, 1 as enabled"
						+ " FROM account WHERE USERNAME=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/actuator/*").permitAll().anyRequest().authenticated().and().httpBasic();
		http.csrf().disable();
	}

}

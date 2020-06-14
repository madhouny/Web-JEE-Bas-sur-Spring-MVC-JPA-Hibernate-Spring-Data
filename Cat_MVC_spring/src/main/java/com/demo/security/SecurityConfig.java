package com.demo.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource datasource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		
		auth.inMemoryAuthentication().withUser("admin").password("{noop}12345").roles("USER", "ADMIN");
		auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("USER");
		/*
		
		auth.jdbcAuthentication()
		.dataSource(datasource)
		.usersByUsernameQuery("select login as principal,password as credentials,active from users where login = ?")
		.authoritiesByUsernameQuery("select login as principal, role as role from users_roles where login = ?")
		.passwordEncoder(passwordEncoder())
		.rolePrefix("ROLE");
		*/
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
	    PasswordEncoder encoder = new BCryptPasswordEncoder();
	    return encoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin();
		http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
		http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");
	}
}

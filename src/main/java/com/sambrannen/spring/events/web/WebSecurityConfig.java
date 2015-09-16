/*
 * Copyright 2010-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sambrannen.spring.events.web;

import static org.springframework.http.HttpMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web security configuration for the Spring Events application.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin").password("test").roles("ADMIN");
	}

	@Configuration
	@Order(1)
	public static class HttpBasicWebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/events/**")
				.authorizeRequests()
					.antMatchers(GET, "/**").permitAll()
					.antMatchers(PUT, "/**").hasRole("ADMIN")
					.antMatchers(POST, "/**").hasRole("ADMIN")
					.antMatchers(DELETE, "/**").hasRole("ADMIN")
					.and()
				.csrf().disable()
				.httpBasic();
		}
	}

	@Configuration
	@Order(2)
	public static class FormLoginWebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.antMatchers("/", "/favicon.ico", "/css/**", "/images/**").permitAll()
					.antMatchers("/form**").hasRole("ADMIN")
					.antMatchers("/h2-console/**").hasRole("ADMIN")
					.and()
				.csrf().disable()
				.formLogin();
		}
	}

}

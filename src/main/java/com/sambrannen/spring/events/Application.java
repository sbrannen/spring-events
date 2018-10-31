/*
 * Copyright 2010-2018 the original author or authors.
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

package com.sambrannen.spring.events;

import com.sambrannen.spring.events.service.EventService;
import com.sambrannen.spring.events.web.EventsController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Central configuration for the <em>Spring Events</em> sample application,
 * powered by Spring Boot.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@SpringBootApplication(scanBasePackageClasses = { EventService.class, EventsController.class })
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth.inMemoryAuthentication()
			.passwordEncoder(new BCryptPasswordEncoder())
			.withUser("admin")
				// "test" is encoded using BCryptPasswordEncoder
				.password("$2a$10$ygTOAyJCpUirdPv9NufL9.IEBz1OHwSdD88Faf/0ZE6.MxWEsTWjW")
				.roles("ADMIN", "ACTUATOR");
		// @formatter:on
	}

}

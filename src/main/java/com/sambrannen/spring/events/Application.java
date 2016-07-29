/*
 * Copyright 2010-2016 the original author or authors.
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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.sambrannen.spring.events.service.EventService;
import com.sambrannen.spring.events.web.EventsController;

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

}

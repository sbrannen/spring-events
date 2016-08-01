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

package com.sambrannen.spring.events.web;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sambrannen.spring.events.domain.Event;
import com.sambrannen.spring.events.service.EventService;

/**
 * Integration tests for the {@link EventsController} with a manually
 * mocked {@link EventService} using JUnit 4 and pre-Spring 4.3
 * configuration mechanisms without Spring Boot.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class EventsControllerWithManuallyMockedServiceTests {

	@Autowired
	EventService eventService;

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;


	@Before
	public void setUp() {
		this.mockMvc = webAppContextSetup(wac).build();
	}

	@Test
	public void listEvents() throws Exception {
		when(eventService.findAll()).thenReturn(singletonList(new Event(1L)));

		mockMvc.perform(get("/"))//
				.andExpect(view().name("event/list"))//
				.andExpect(model().attribute("events", hasSize(1)));
	}


	@Configuration
	@EnableWebMvc
	@ComponentScan(basePackageClasses = EventsController.class)
	static class Config {

		@Bean
		EventService eventService() {
			return Mockito.mock(EventService.class);
		}
	}

}

/*
 * Copyright 2002-2015 the original author or authors.
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sambrannen.spring.events.domain.Event;
import com.sambrannen.spring.events.repository.EventRepository;
import com.sambrannen.spring.events.service.EventService;
import com.sambrannen.spring.events.service.StandardEventService;
import com.sambrannen.spring.events.web.EventsController;

/**
 * @author Nicolas Frankel
 * @author Sam Brannen
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration
@WebAppConfiguration
public class EventsControllerTests {

    @ClassRule
    public static final SpringClassRule SPRING_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springRule = new SpringMethodRule();

    @Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void shouldDisplayRepositoryItemsInitially() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(view().name("event/list"))
            .andExpect(model().attribute("events", hasSize(1)));
    }


	@Configuration
	@EnableWebMvc
	static class TestConfiguration {

		@Bean
		public EventRepository eventRepository() {
			EventRepository repository = Mockito.mock(EventRepository.class);
			Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(new Event(1L)));
			return repository;
		}

		@Bean
		public EventService eventService() {
			return new StandardEventService(eventRepository());
		}

		@Bean
		public EventsController eventsController() {
			return new EventsController(eventService());
		}
	}

}

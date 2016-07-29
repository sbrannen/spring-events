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
import static org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint.SYSTEM_ERR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.sambrannen.spring.events.domain.Event;
import com.sambrannen.spring.events.service.EventService;

/**
 * Integration tests for the {@link EventsController} with a mocked
 * {@link EventService}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc(print = SYSTEM_ERR)
@Import(WebSecurityConfig.class)
class EventsControllerWithMockedServiceTests {

	@MockBean
	EventService eventService;

	@Autowired
	MockMvc mockMvc;


	@Test
	void listEvents() throws Exception {
		when(eventService.findAll()).thenReturn(singletonList(new Event(1L)));

		mockMvc.perform(get("/"))//
				.andExpect(view().name("event/list"))//
				.andExpect(model().attribute("events", hasSize(1)));
	}

}

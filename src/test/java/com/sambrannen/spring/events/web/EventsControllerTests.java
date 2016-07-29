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

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

/**
 * JUnit based integration tests for the {@link EventsController}.
 *
 * @author Sam Brannen
 * @since 1.0
 * @see EventsControllerTestNgTests
 */
@SpringEventsWebTest
class EventsControllerTests {

	@Autowired
	MockMvc mockMvc;


	@Test
	@DisplayName("Home page should show at least 10 items")
	void shouldDisplayAtLeastTenItemsInitially() throws Exception {
		mockMvc.perform(get("/"))//
				.andExpect(view().name("event/list"))//
				.andExpect(model().attribute("events", hasSize(greaterThanOrEqualTo(10))));
	}

}

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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sambrannen.spring.events.Application;

/**
 * TestNG based integration tests for the {@link EventsController}.
 *
 * @author Nicolas Frankel
 * @since 1.0
 * @see EventsControllerIT
 */
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class EventsControllerTestNgIT extends AbstractTestNGSpringContextTests {

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	@BeforeMethod
	protected void setUp() {
		mockMvc = webAppContextSetup(wac).build();
	}

	@Test
	public void shouldDisplayTenItemsInitially() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(view().name("event/list"))
			.andExpect(model().attribute("events", hasSize(greaterThanOrEqualTo(10))));
    }

}

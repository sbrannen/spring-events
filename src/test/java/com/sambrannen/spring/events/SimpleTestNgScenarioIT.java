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

package com.sambrannen.spring.events;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sambrannen.spring.events.domain.Event;
import com.sambrannen.spring.events.repository.EventRepository;

/**
 * TestNG-based scenario tests for the Spring Events application.
 *
 * @author Nicolas Frankel
 * @author Sam Brannen
 * @since 1.0
 */
@SpringApplicationConfiguration(Application.class)
@TestExecutionListeners(WithSecurityContextTestExecutionListener.class)
@WebAppConfiguration
public class SimpleTestNgScenarioIT extends AbstractTestNGSpringContextTests {

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	@Autowired
	EventRepository repository;


	@BeforeClass
	protected void setUpBeforeClass() {
		mockMvc = webAppContextSetup(wac).build();
	}

	@Test
	public void shouldDisplayAtLeastTenItemsInitially() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(view().name("event/list"))
			.andExpect(model().attribute("events", hasSize(greaterThanOrEqualTo(10))));
	}

	@Test(dependsOnMethods = "shouldDisplayAtLeastTenItemsInitially")
	public void shouldDisplayEventForm() throws Exception {
		mockMvc.perform(get("/form"))
			.andExpect(view().name("event/form"))
			.andExpect(model().attribute("event", isA(Event.class)));
	}

	@Test(dependsOnMethods = "shouldDisplayEventForm")
	@WithMockUser(roles = "ADMIN")
	public void shouldAddNewEvent() throws Exception {
		mockMvc.perform(post("/form").param("name", "THE Event").param("location", "Earth"))
			.andExpect(redirectedUrl("/"));

		assertThat(repository.count()).isGreaterThanOrEqualTo(11);
	}

	@Test(dependsOnMethods = "shouldAddNewEvent")
	public void shouldDisplayAtLeastElevenItemsInTheEnd() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(view().name("event/list"))
			.andExpect(model().attribute("events", hasSize(greaterThanOrEqualTo(11))));
	}

}

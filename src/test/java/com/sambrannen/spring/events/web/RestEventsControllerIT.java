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

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.sambrannen.spring.events.Application;

/**
 * Integration tests for the {@link RestEventsController}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@Transactional
public class RestEventsControllerIT {

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;


	@Before
	public void setUpMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)//
			.apply(springSecurity())//
			// .alwaysDo(print(System.err))
			.build();
	}


	@Test
	public void retrieveAllEvents() throws Exception {
		mockMvc.perform(get("/events").accept(APPLICATION_JSON))//
			.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))//
			.andExpect(status().isOk())//
			.andExpect(jsonPath("$[8]").exists())//
			.andExpect(jsonPath("$[?(@.name =~ /Spring I\\/O/)].location",
					hasItems("Madrid", "Barcelona")));
	}

	@Test
	public void retrieveEvent() throws Exception {
		mockMvc.perform(get("/events/{id}", 9).accept(APPLICATION_JSON))//
			.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))//
			.andExpect(status().isOk())//
			.andExpect(jsonPath("$.id", is(9)))//
			.andExpect(jsonPath("$.eventDate", is("2015-04-30")))//
			.andExpect(jsonPath("$.name", is("Spring I/O")))//
			.andExpect(jsonPath("$.location", is("Barcelona")));
	}

	@Test
	public void retrieveNonexistentEvent() throws Exception {
		mockMvc.perform(get("/events/{id}", 12345).accept(APPLICATION_JSON))//
			.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void createEvent() throws Exception {
		mockMvc.perform(
			post("/events/")
				.contentType(APPLICATION_JSON)//
				.content("{\"name\": \"Spring!\", \"location\": \"Integration Test\"}")//
				//.with(user("admin").roles("ADMIN"))//
			)
			.andExpect(status().isCreated());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void updateEvent() throws Exception {
		mockMvc.perform(
			put("/events/{id}", 9).contentType(APPLICATION_JSON)
				.content("{\"name\": \"Edited\", \"location\": \"Integration Test\"}"))//
			.andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void deleteEvent() throws Exception {
		mockMvc.perform(delete("/events/{id}", 9))//
			.andExpect(status().isNoContent());
	}

}

// Example JSON Output
//
// [
// {"id":1,"eventDate":"2010-02-25","name":"Spring Geek Night","location":"Zurich"},
// {"id":2,"eventDate":"2011-02-17","name":"Spring I/O","location":"Madrid"},
// {"id":3,"eventDate":"2011-10-28","name":"SpringOne 2GX","location":"Chicago"},
// {"id":4,"eventDate":"2012-10-18","name":"SpringOne 2GX","location":"Washington, D.C."},
// {"id":5,"eventDate":"2013-11-14","name":"Devoxx","location":"Antwerp"},
// {"id":6,"eventDate":"2014-04-22","name":"Spring User Group","location":"Atlanta"},
// {"id":7,"eventDate":"2014-09-10","name":"SpringOne 2GX","location":"Dallas"},
// {"id":8,"eventDate":"2014-11-06","name":"Spring eXchange","location":"London"},
// {"id":9,"eventDate":"2015-04-30","name":"Spring I/O","location":"Barcelona"}
// ]

// curl -H "Accept:application/json" http://localhost:8080/events | json_pp

// curl -H "Accept:application/json" http://localhost:8080/events/9 | json_pp

// curl -u admin:test -i -X POST -H "Content-Type:application/json" http://localhost:8080/events/ -d '{"name": "Spring!", "location": "Command Line"}'

// curl -u admin:test -i -X PUT -H "Content-Type:application/json" http://localhost:8080/events/9 -d '{"name": "Edited", "location": "Command Line"}'

// curl -u admin:test -i -X DELETE http://localhost:8080/events/9

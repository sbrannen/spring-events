/*
 * Copyright 2010-2017 the original author or authors.
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

package com.sambrannen.spring.events.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.sambrannen.spring.events.domain.Event;
import com.sambrannen.spring.events.repository.EventRepository;

/**
 * Integration tests for the {@link EventService}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class EventsServiceTests {

	@Autowired
	EventService service;

	@Autowired
	EventRepository repo;


	@Test
	@WithMockUser(roles = "ADMIN")
	void save() throws Exception {
		service.save(new Event("new event", "integration test"));

		assertThat(repo.findAll().stream().filter(e -> e.getName().equals("new event")).findFirst()).isPresent();
	}

}

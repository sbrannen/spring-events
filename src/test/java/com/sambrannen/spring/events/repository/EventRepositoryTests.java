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

package com.sambrannen.spring.events.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.sambrannen.spring.events.domain.Event;

/**
 * Integration tests for the {@link EventRepository}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@SpringJUnitConfig(TestRepositoryConfig.class)
@Transactional
class EventRepositoryTests {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	EventRepository repo;


	@Test
	void findAll() {
		List<Event> events = repo.findAll();
		assertThat(events).isNotNull();
		assertThat(events.size()).isGreaterThan(0);
	}

	@Test
	void findById() {
		assertThat(repo.findById(1L)).isPresent();
		assertThat(repo.findById(999L)).isNotPresent();
	}

	@Test
	void save() {
		final int numRowsInTable = countNumEvents();
		final LocalDate tomorrow = LocalDate.now().plusDays(1);

		Event event = new Event();
		event.setName("test event");
		event.setLocation("test suite");
		event.setEventDate(tomorrow);

		Event savedEvent = repo.save(event);
		repo.flush();

		assertThat(savedEvent.getId()).isNotNull();
		assertNumEvents(numRowsInTable + 1);
		Event retrievedSavedEvent = repo.findById(savedEvent.getId()).orElse(null);
		assertThat(retrievedSavedEvent).isEqualTo(event);
		assertThat(retrievedSavedEvent.getEventDate()).isEqualTo(tomorrow);
	}

	@Test
	void update() {
		final int numRowsInTable = countNumEvents();

		Event event = repo.findById(1L).orElse(null);
		assertThat(event).isNotNull();
		event.setName("updated name");

		Event updatedEvent = repo.save(event);
		repo.flush();

		assertNumEvents(numRowsInTable);
		String updatedName = lookUpNameInDatabase(updatedEvent);
		assertThat(updatedName).isEqualTo("updated name");
	}

	@Test
	void delete() {
		final int numRowsInTable = countNumEvents();

		Event event = repo.findById(1L).orElse(null);
		assertThat(event).isNotNull();
		repo.delete(event);
		repo.flush();
		assertNumEvents(numRowsInTable - 1);
	}

	private String lookUpNameInDatabase(Event event) {
		return jdbcTemplate.queryForObject("select name from event where id=?", String.class, event.getId());
	}

	private int countNumEvents() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, "event");
	}

	private void assertNumEvents(int expectedNumRows) {
		assertThat(countNumEvents()).isEqualTo(expectedNumRows);
	}

}

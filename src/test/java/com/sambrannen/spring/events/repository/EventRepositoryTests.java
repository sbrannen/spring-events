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

package com.sambrannen.spring.events.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.sambrannen.spring.events.domain.Event;
import com.sambrannen.spring.events.repository.EventRepository;

/**
 * Integration tests for the {@link EventRepository}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@SpringApplicationConfiguration(TestRepositoryConfig.class)
public class EventRepositoryTests extends AbstractTransactionalJUnit4SpringContextTests {

	private static final String EVENTS_TABLE = "event";

	@Autowired
	EventRepository repo;


	@Test
	public void findAll() {
		List<Event> events = repo.findAll();
		assertThat(events).isNotNull();
		assertThat(events.size()).isGreaterThan(0);
	}

	@Test
	public void findOne() {
		Event event = repo.findOne(1L);
		assertThat(event).isNotNull();
	}

	@Test
	public void save() {
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
		Event retrievedSavedEvent = repo.findOne(savedEvent.getId());
		assertThat(retrievedSavedEvent).isEqualTo(event);
		assertThat(retrievedSavedEvent.getEventDate()).isEqualTo(tomorrow);
	}

	@Test
	public void update() {
		final int numRowsInTable = countNumEvents();

		Event event = repo.findOne(1L);
		assertThat(event).isNotNull();
		event.setName("updated name");

		Event updatedEvent = repo.save(event);
		repo.flush();

		assertNumEvents(numRowsInTable);
		String updatedName = jdbcTemplate.queryForObject("select name from event where id=?", String.class,
			updatedEvent.getId());
		assertThat(updatedName).isEqualTo("updated name");
	}

	@Test
	public void delete() {
		final int numRowsInTable = countNumEvents();

		Event event = repo.findOne(1L);
		assertThat(event).isNotNull();
		repo.delete(event);
		repo.flush();
		assertNumEvents(numRowsInTable - 1);
	}

	private int countNumEvents() {
		return countRowsInTable(EVENTS_TABLE);
	}

	private void assertNumEvents(int expectedNumRows) {
		assertThat(countNumEvents()).isEqualTo(expectedNumRows);
	}

}

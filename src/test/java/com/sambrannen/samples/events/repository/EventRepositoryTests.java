/*
 * Copyright 2010-2014 the original author or authors.
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

package com.sambrannen.samples.events.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.sambrannen.samples.events.domain.Event;

/**
 * Integration tests for the {@link EventRepository}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@SpringApplicationConfiguration(classes = TestRepositoryConfig.class)
public class EventRepositoryTests extends AbstractTransactionalJUnit4SpringContextTests {

	private static final String EVENTS_TABLE = "event";

	@Autowired
	EventRepository repo;


	@Test
	public void findAll() {
		List<Event> events = repo.findAll();
		assertNotNull(events);
		assertTrue(events.size() > 0);
	}

	@Test
	public void findOne() {
		Event event = repo.findOne(1L);
		assertNotNull(event);
	}

	@Test
	public void save() {
		final int numRowsInTable = countNumEvents();

		Event event = new Event();
		event.setName("test event");
		event.setLocation("test suite");

		Event savedEvent = repo.save(event);
		repo.flush();

		assertNotNull(savedEvent.getId());
		assertNumEvents(numRowsInTable + 1);
		assertEquals(event, repo.findOne(savedEvent.getId()));
	}

	@Test
	public void update() {
		final int numRowsInTable = countNumEvents();

		Event event = repo.findOne(1L);
		assertNotNull(event);
		event.setName("updated name");

		Event updatedEvent = repo.save(event);
		repo.flush();

		assertNumEvents(numRowsInTable);
		String updatedName = jdbcTemplate.queryForObject("select name from event where id=?", String.class,
			updatedEvent.getId());
		assertEquals("updated name", updatedName);
	}

	@Test
	public void delete() {
		final int numRowsInTable = countNumEvents();

		Event event = repo.findOne(1L);
		assertNotNull(event);
		repo.delete(event);
		repo.flush();
		assertNumEvents(numRowsInTable - 1);
	}

	private int countNumEvents() {
		return countRowsInTable(EVENTS_TABLE);
	}

	private void assertNumEvents(int expectedNumRows) {
		assertEquals("Number of rows in table [" + EVENTS_TABLE + "].", expectedNumRows, countNumEvents());
	}

}

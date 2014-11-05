/*
 * Copyright 2002-2014 the original author or authors.
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.sambrannen.samples.events.Application;
import com.sambrannen.samples.events.domain.Event;

/**
 * Integration tests for the {@link EventRepository}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@SpringApplicationConfiguration(classes = Application.class)
public class EventRepositoryTests extends AbstractTransactionalJUnit4SpringContextTests {

	private static final String EVENTS_TABLES = "event";
	private static final int INITIAL_NUM_EVENTS = 8;

	@Autowired
	EventRepository repo;

	@PersistenceContext
	EntityManager em;


	@Test
	public void findAll() {
		List<Event> events = repo.findAll();
		assertNotNull(events);
		assertEquals(INITIAL_NUM_EVENTS, events.size());
	}

	@Test
	public void findOne() {
		Event event = repo.findOne(1L);
		assertNotNull(event);
	}

	@Test
	public void saveWithMinimumRequiredFields() {
		assertNumEvents(INITIAL_NUM_EVENTS);

		Event event = new Event();
		event.setName("test event");

		Event savedEvent = repo.save(event);
		repo.flush();

		assertNotNull(savedEvent.getId());
		assertNumEvents(INITIAL_NUM_EVENTS + 1);

		event.setId(new Long(INITIAL_NUM_EVENTS + 1));
		assertEquals(event, repo.findOne(savedEvent.getId()));
	}

	@Test
	public void update() {
		assertNumEvents(INITIAL_NUM_EVENTS);

		Event event = repo.findOne(1L);
		assertNotNull(event);
		event.setName("updated name");

		Event updatedEvent = repo.save(event);
		repo.flush();

		assertNumEvents(INITIAL_NUM_EVENTS);
		String updatedName = jdbcTemplate.queryForObject("select name from event where id=?", String.class,
			updatedEvent.getId());

		assertEquals("updated name", updatedName);
	}

	@Test
	public void delete() {
		Event event = repo.findOne(1L);
		assertNotNull(event);
		repo.delete(event);
		repo.flush();
		assertNumEvents(INITIAL_NUM_EVENTS - 1);
	}

	private void assertNumEvents(int expectedNumRows) {
		int numRowsInTable = countRowsInTable(EVENTS_TABLES);
		assertEquals("Number of rows in table [" + EVENTS_TABLES + "].", expectedNumRows, numRowsInTable);
	}

}

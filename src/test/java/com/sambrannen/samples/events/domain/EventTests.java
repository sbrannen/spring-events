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

package com.sambrannen.samples.events.domain;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 * Unit tests for the {@link Event} entity.
 *
 * @author Sam Brannen
 * @since 1.0
 */
public class EventTests {

	@Test
	public void gettersAndSetters() {
		Event event = new Event();
		event.setId(99L);
		event.setEventDate(new Date());
		event.setName("Test Event");
		event.setDescription("testing...");

		assertEquals(99L, event.getId().longValue());
		assertEquals("Test Event", event.getName());
		assertEquals("testing...", event.getDescription());
	}

	@Test
	public void equalsAndHashCode() {
		Event event1 = createEvent(1L);
		Event event2 = createEvent(2L);

		assertEquals(event1, event1);
		assertEquals(event2, event2);
		assertNotEquals(event1, event2);

		Event event3 = createEvent("event 3");
		Event event4 = createEvent("event 4");

		assertEquals(event3, event3);
		assertEquals(event4, event4);
		assertEquals(event3, event4);
	}

	private Event createEvent(long id) {
		Event event1 = new Event();
		event1.setId(id);
		event1.setEventDate(new Date());
		event1.setName("Test Event");
		event1.setDescription("testing...");
		return event1;
	}

	private Event createEvent(String name) {
		Event event1 = new Event();
		event1.setId(99L);
		event1.setEventDate(new Date());
		event1.setName(name);
		event1.setDescription("testing...");
		return event1;
	}

}

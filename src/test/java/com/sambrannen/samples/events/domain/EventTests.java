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

package com.sambrannen.samples.events.domain;

import static org.junit.Assert.*;

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
		Event event = new Event(99L);
		event.setName("Test Event");
		event.setLocation("Unit Test");

		assertEquals(99L, event.getId().longValue());
		assertEquals("Test Event", event.getName());
		assertEquals("Unit Test", event.getLocation());
	}

	@Test
	public void equalsAndHashCode() {
		Event event1 = createEvent(1L);
		Event event2 = createEvent(2L);

		assertEqualsAndHashCode(event1, event1);
		assertEqualsAndHashCode(event2, event2);
		assertEqualsAndHashCode(event1, event2);

		Event event3 = createEvent("event 3");
		Event event4 = createEvent("event 4");

		assertEqualsAndHashCode(event3, event3);
		assertEqualsAndHashCode(event4, event4);
		assertNotEquals(event3, event4);
		assertNotEquals(event3.hashCode(), event4.hashCode());
	}

	private void assertEqualsAndHashCode(Event e1, Event e2) {
		assertEquals(e1, e2);
		assertEquals(e1.hashCode(), e2.hashCode());
	}

	private Event createEvent(Long id) {
		return createEvent(id, "Test Event");
	}

	private Event createEvent(String name) {
		return createEvent(99L, name);
	}

	private Event createEvent(Long id, String name) {
		Event event = new Event(id);
		event.setName(name);
		event.setLocation("Unit Test");
		return event;
	}

}

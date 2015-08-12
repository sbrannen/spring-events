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

package com.sambrannen.samples.events.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

/**
 * Unit tests for the {@link Event} entity, basically just verifying that
 * Lombok is working.
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

		assertThat(event.getId()).isEqualTo(99L);
		assertThat(event.getName()).isEqualTo("Test Event");
		assertThat(event.getLocation()).isEqualTo("Unit Test");
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
		assertThat(event3).isNotEqualTo(event4);
		assertThat(event3.hashCode()).isNotEqualTo(event4.hashCode());
	}

	private void assertEqualsAndHashCode(Event e1, Event e2) {
		assertThat(e1).isEqualTo(e2);
		assertThat(e1.hashCode()).isEqualTo(e2.hashCode());
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

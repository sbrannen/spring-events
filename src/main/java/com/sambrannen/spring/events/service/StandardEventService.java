/*
 * Copyright 2002-2015 the original author or authors.
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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.composed.stereotype.TransactionalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.sambrannen.spring.events.domain.Event;
import com.sambrannen.spring.events.repository.EventRepository;

/**
 * Standard implementation of the {@link EventService} API which delegates
 * to an {@link EventRepository}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@TransactionalService(readOnly = true)
public class StandardEventService implements EventService {

	private final EventRepository repository;


	@Autowired
	public StandardEventService(EventRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Event> findAll() {
		return repository.findAll();
	}

	@Override
	public Event findById(Long id) {
		Event event = repository.findOne(id);
		if (event == null) {
			throw new EventNotFoundException("Could not find Event with ID [" + id + "]");
		}
		return event;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional(readOnly = false)
	@Override
	public Event save(Event event) {
		return repository.save(event);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional(readOnly = false)
	@Override
	public void deleteById(Long id) {
		repository.delete(id);
	}

}

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

package com.sambrannen.samples.events.web;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.sambrannen.samples.events.domain.Event;
import com.sambrannen.samples.events.repository.EventRepository;
import com.sambrannen.samples.events.web.annotation.Delete;
import com.sambrannen.samples.events.web.annotation.Get;
import com.sambrannen.samples.events.web.annotation.Post;

/**
 * RESTful controller for {@link Event events}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@RestController
@RequestMapping("/events")
public class RestEventController {

	private final EventRepository repository;


	@Autowired
	public RestEventController(EventRepository eventRepository) {
		this.repository = eventRepository;
	}

	@Get
	public List<Event> retrieveAllEvents() {
		return repository.findAll();
	}

	@Get("/{id}")
	public Event retrieveEvent(@PathVariable Long id) {
		return repository.findOne(id);
	}

	@Post
	public HttpEntity<Void> createEvent(@RequestBody Event postedEvent) {
		Event savedEvent = repository.save(postedEvent);

		UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodCall(
			on(RestEventController.class).retrieveEvent(savedEvent.getId())).build();

		return ResponseEntity.noContent().location(uriComponents.encode().toUri()).build();
	}

	@Delete("/{id}")
	public void deleteEvent(@PathVariable Long id) {
		repository.delete(id);
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void handleDatabaseConstraintViolation() {
		/* no-op */
	}

}

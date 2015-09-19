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

package com.sambrannen.spring.events.web;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.composed.web.Delete;
import org.springframework.composed.web.Get;
import org.springframework.composed.web.Post;
import org.springframework.composed.web.Put;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.sambrannen.spring.events.domain.Event;
import com.sambrannen.spring.events.service.EventService;

/**
 * RESTful controller for {@link Event events}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@RestController
@RequestMapping("/events")
public class RestEventsController {

	private final EventService service;

	@Autowired
	public RestEventsController(EventService service) {
		this.service = service;
	}

	@Get
	public List<Event> retrieveAllEvents() {
		return service.findAll();
	}

	@Get("/{id}")
	public Event retrieveEvent(@PathVariable Long id) {
		return service.findById(id);
	}

	@Post
	public HttpEntity<Void> createEvent(@RequestBody Event postedEvent) {
		Event savedEvent = service.save(postedEvent);

		UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodCall(
			on(RestEventsController.class).retrieveEvent(savedEvent.getId())).build();

		return ResponseEntity.created(uriComponents.encode().toUri()).build();
	}

	@Put("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void updateEvent(@RequestBody Event updatedEvent) {
		service.save(updatedEvent);
	}

	@Delete("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void deleteEvent(@PathVariable Long id) {
		service.deleteById(id);
	}

}

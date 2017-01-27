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

package com.sambrannen.spring.events.web;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.List;

import org.springframework.beans.DirectFieldAccessor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

	public RestEventsController(EventService service) {
		this.service = service;
	}

	@GetMapping
	public List<Event> retrieveAllEvents() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Event retrieveEvent(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping
	public HttpEntity<Void> createEvent(@RequestBody Event postedEvent) {
		Event savedEvent = service.save(postedEvent);

		UriComponents uriComponents = fromMethodCall(
			on(getClass()).retrieveEvent(savedEvent.getId())).build();

		return ResponseEntity.created(uriComponents.encode().toUri()).build();
	}

	@PutMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void updateEvent(@RequestBody Event updatedEvent, @PathVariable Long id) {
		// Be default, for security reasons, we do not allow an 'id' field
		// to be bound to a domain entity by external clients. However, since
		// this is an update request, the 'id' must be set in the event.
		// Otherwise, we save a new event instead of updating the existing one.
		// And instead of introducing a public setId() method in Event, we
		// choose to use Spring's DirectFieldAccessor to set the 'id', thereby
		// allowing us to continue to discourage changing an ID of an existing
		// event.
		//
		// See: GlobalControllerAdvice.configureBinding(WebDataBinder)
		new DirectFieldAccessor(updatedEvent).setPropertyValue("id", id);

		service.save(updatedEvent);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void deleteEvent(@PathVariable Long id) {
		service.deleteById(id);
	}

}

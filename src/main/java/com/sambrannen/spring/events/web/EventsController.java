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

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.composed.web.Get;
import org.springframework.composed.web.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.sambrannen.spring.events.domain.Event;
import com.sambrannen.spring.events.repository.EventRepository;

/**
 * Spring MVC controller for displaying and creating {@link Event events}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@Controller
public class EventsController {

	private static final String LIST_VIEW_NAME = "event/list";
	private static final String FORM_VIEW_NAME = "event/form";

	private static final Log log = LogFactory.getLog(EventsController.class);

	private final EventRepository repository;


	@Autowired
	public EventsController(EventRepository repository) {
		this.repository = repository;
	}

	@Get("/")
	public String list(Model model) {
		log.debug("Displaying all events");
		model.addAttribute("events", repository.findAll());
		return LIST_VIEW_NAME;
	}

	@Get("/form")
	public String edit(Model model) {
		log.debug("Displaying event form");
		model.addAttribute(new Event());
		return FORM_VIEW_NAME;
	}

	@Post("/form")
	public String submit(@Valid Event event, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.debug("Redisplaying event form");
			return FORM_VIEW_NAME;
		}

		log.debug("Submitting event form: " + event);
		repository.save(event);
		return "redirect:/";
	}

}

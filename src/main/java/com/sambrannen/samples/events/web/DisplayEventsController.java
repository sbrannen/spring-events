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

package com.sambrannen.samples.events.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sambrannen.samples.events.domain.Event;
import com.sambrannen.samples.events.repository.EventRepository;

/**
 * Spring MVC controller for displaying all {@link Event events}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@Controller
@RequestMapping("/")
public class DisplayEventsController {

	private static final Log log = LogFactory.getLog(DisplayEventsController.class);

	private final EventRepository repository;


	@Autowired
	public DisplayEventsController(EventRepository repository) {
		this.repository = repository;
	}

	@RequestMapping(method = GET)
	public final String list(Model model) {
		log.debug("Displaying all events.");
		model.addAttribute("events", repository.findAll());
		return "event/list";
	}

}

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

package com.sambrannen.samples.events.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sambrannen.samples.events.domain.Event;
import com.sambrannen.samples.events.repository.EventRepository;

/**
 * Spring MVC controller for submitting a new {@link Event}.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@Controller
@RequestMapping("/new*")
public class NewEventController {

	private static final String FORM_VIEW_NAME = "event/new";
	private static final Log log = LogFactory.getLog(NewEventController.class);

	private final EventRepository repository;


	@Autowired
	public NewEventController(EventRepository repository) {
		this.repository = repository;
	}

	@InitBinder
	public void configureBinding(WebDataBinder binder) {
		binder.setDisallowedFields("id");
	}

	@RequestMapping(method = GET)
	public String edit(Model model) {
		log.debug("Displaying event form.");
		model.addAttribute(new Event());
		return FORM_VIEW_NAME;
	}

	@RequestMapping(method = POST)
	public String submit(@Valid Event event, BindingResult bindingResult) throws Exception {
		log.debug("Submitting event form [" + event + "].");

		if (bindingResult.hasErrors()) {
			return FORM_VIEW_NAME;
		}
		// else
		repository.save(event);
		return "redirect:/";
	}

}

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

package com.sambrannen.spring.events.service;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown to signal that an event could not be found for a given set of
 * search criteria.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@ResponseStatus(NOT_FOUND)
public class EventNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2411738770351557081L;


	public EventNotFoundException(String message) {
		super(message);
	}

	public EventNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}

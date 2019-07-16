/*
 * Copyright 2010-2019 the original author or authors.
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

package com.sambrannen.spring.events.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Domain entity for Spring events.
 *
 * @author Sam Brannen
 * @since 1.0
 */
@Entity
public class Event implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "{errors.required}")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate eventDate;

	@NotNull(message = "{errors.required}")
	@Size(min = 5, max = 30, message = "{errors.range}")
	private String name;

	@NotNull(message = "{errors.required}")
	@Size(min = 5, max = 30, message = "{errors.range}")
	private String location;


	public Event() {
		// It's necessary to set this here instead of at the field declaration
		// since Jackson will otherwise set the value to null.
		this.eventDate = LocalDate.now();
	}

	public Event(Long id) {
		this();
		this.id = id;
	}

	public Event(String name, String location) {
		this();
		this.name = name;
		this.location = location;
	}

	public Event(Long id, LocalDate eventDate, String name, String location) {
		this(name, location);
		this.id = id;
		this.eventDate = eventDate;
	}

	public Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		}
		else if (!eventDate.equals(other.eventDate))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		}
		else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", eventDate=" + eventDate + ", name=" + name + ", location=" + location + "]";
	}

}

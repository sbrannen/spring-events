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

package org.springframework.composed.web.rest.xml;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sam Brannen
 * @since 1.0
 */
@RequestMapping(method = POST, consumes = APPLICATION_XML_VALUE)
@ResponseStatus(CREATED)
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface PostXml {

	@AliasFor(annotation = RequestMapping.class, attribute = "name")
	String name() default "";

	@AliasFor(annotation = RequestMapping.class, attribute = "path")
	String[] value() default {};

	@AliasFor(annotation = RequestMapping.class, attribute = "path")
	String[] path() default {};

	@AliasFor(annotation = RequestMapping.class, attribute = "params")
	String[] params() default {};

	@AliasFor(annotation = RequestMapping.class, attribute = "headers")
	String[] headers() default {};

}

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

package org.springframework.composed.web.scopes;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import static org.springframework.web.context.WebApplicationContext.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Sam Brannen
 * @since 1.0
 * @see Scope
 * @see WebApplicationContext#SCOPE_SESSION
 */
@Scope(SCOPE_SESSION)
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@Documented
public @interface SessionScoped {

	/**
	 * @see Scope#proxyMode
	 */
	@AliasFor(annotation = Scope.class, attribute = "proxyMode")
	ScopedProxyMode proxyMode() default ScopedProxyMode.DEFAULT;

}

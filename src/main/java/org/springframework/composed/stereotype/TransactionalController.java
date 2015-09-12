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

package org.springframework.composed.stereotype;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sam Brannen
 * @since 1.0
 */
@Transactional
@Controller
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface TransactionalController {

	/**
	 * @see Controller#value
	 */
	String value() default "";

	@AliasFor(annotation = Transactional.class, attribute = "transactionManager")
	String transactionManager() default "";

	@AliasFor(annotation = Transactional.class, attribute = "propagation")
	Propagation propagation() default Propagation.REQUIRED;

	@AliasFor(annotation = Transactional.class, attribute = "isolation")
	Isolation isolation() default Isolation.DEFAULT;

	@AliasFor(annotation = Transactional.class, attribute = "timeout")
	int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

	@AliasFor(annotation = Transactional.class, attribute = "readOnly")
	boolean readOnly() default false;

	@AliasFor(annotation = Transactional.class, attribute = "rollbackFor")
	Class<? extends Throwable>[] rollbackFor() default {};

	@AliasFor(annotation = Transactional.class, attribute = "rollbackForClassName")
	String[] rollbackForClassName() default {};

	@AliasFor(annotation = Transactional.class, attribute = "noRollbackFor")
	Class<? extends Throwable>[] noRollbackFor() default {};

	@AliasFor(annotation = Transactional.class, attribute = "noRollbackForClassName")
	String[] noRollbackForClassName() default {};

}

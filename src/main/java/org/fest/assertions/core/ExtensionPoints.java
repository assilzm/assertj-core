/*
 * Created on Sep 26, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010 the original author or authors.
 */
package org.fest.assertions.core;

/**
 * Mechanism for extending assertion classes.
 * @param <S> the "self" type of this assertion class. Please read
 * &quot;<a href="http://bit.ly/anMa4g" target="_blank">Emulating 'self types' using Java Generics to simplify fluent
 * API implementation</a>&quot; for more details.
 * @param <A> the type of the "actual" value.
 *
 * @author Alex Ruiz
 */
public interface ExtensionPoints<S, A> {

  /**
   * Verifies that the actual value satisfies the given condition.
   * @param condition the given condition.
   * @return {@code this ExtensionPoints} object.
   * @throws NullPointerException if the given condition is {@code null}.
   * @throws AssertionError if the actual value does not satisfy the given condition.
   * @see #is(Condition)
   */
  S satisfies(Condition<A> condition);

  /**
   * Verifies that the actual value does not satisfy the given condition.
   * @param condition the given condition.
   * @return {@code this ExtensionPoints} object.
   * @throws NullPointerException if the given condition is {@code null}.
   * @throws AssertionError if the actual value satisfies the given condition.
   * @see #isNot(Condition)
   */
  S doesNotSatisfy(Condition<A> condition);

  /**
   * Alias for <code>{@link #satisfies(Condition)}</code>.
   * @param condition the given condition.
   * @return {@code this ExtensionPoints} object.
   * @throws NullPointerException if the given condition is {@code null}.
   * @throws AssertionError if the actual value does not satisfy the given condition.
   */
  S is(Condition<A> condition);

  /**
   * Alias for <code>{@link #doesNotSatisfy(Condition)}</code>.
   * @param condition the given condition.
   * @return {@code this ExtensionPoints} object.
   * @throws NullPointerException if the given condition is {@code null}.
   * @throws AssertionError if the actual value satisfies the given condition.
   */
  S isNot(Condition<A> condition);
}
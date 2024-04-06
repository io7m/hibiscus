/*
 * Copyright © 2023 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.hibiscus.api;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Flow;

/**
 * The type of RPC clients.
 *
 * @param <M> The type of messages
 * @param <P> The type of connection parameters
 * @param <X> the type of exceptions
 */

public interface HBClientType<
  M extends HBMessageType,
  P extends HBConnectionParametersType,
  X extends Exception>
  extends HBClientCloseableType<X>
{
  /**
   * @return The current client state
   */

  HBStateType stateNow();

  /**
   * @return A stream of state updates
   */

  Flow.Publisher<HBStateType> state();

  /**
   * Attempt to connect to the server.
   *
   * @param parameters The parameters
   *
   * @return The message returned on success
   *
   * @throws X                    On errors
   * @throws InterruptedException On interruption
   */

  Optional<M> connect(
    P parameters)
    throws X, InterruptedException;

  /**
   * Send a message to the server without waiting for a response.
   *
   * @param message The message
   *
   * @throws X                    On errors
   * @throws InterruptedException On interruption
   */

  void send(M message)
    throws X, InterruptedException;

  /**
   * Send a message to the server, waiting until the server sends a response
   * to the message.
   *
   * @param message The message
   * @param <R>     The type of responses
   *
   * @return The response
   *
   * @throws X                    On errors
   * @throws InterruptedException On interruption
   */

  <R extends M> R ask(M message)
    throws X, InterruptedException;

  /**
   * Read a message from the connection, waiting at most {@code timeout} until
   * giving up.
   *
   * @param timeout The timeout
   *
   * @return The message, if any
   *
   * @throws X                    On errors
   * @throws InterruptedException On interruption
   */

  Optional<M> receive(Duration timeout)
    throws X, InterruptedException;

  /**
   * Disconnect from the server.
   *
   * @throws X                    On errors
   * @throws InterruptedException On interruption
   */

  void disconnect()
    throws X, InterruptedException;
}

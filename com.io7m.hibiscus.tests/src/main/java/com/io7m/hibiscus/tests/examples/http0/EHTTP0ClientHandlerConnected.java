/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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


package com.io7m.hibiscus.tests.examples.http0;

import com.io7m.hibiscus.api.HBConnection;
import com.io7m.hibiscus.basic.HBConnectionError;
import com.io7m.hibiscus.basic.HBConnectionResultType;

import java.nio.channels.AlreadyConnectedException;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

public final class EHTTP0ClientHandlerConnected
  extends EHTTP0ClientHandlerAbstract
{
  private final HBConnection<EHTTP0MessageType, EHTTP0Exception> connection;

  EHTTP0ClientHandlerConnected(
    final HBConnection<EHTTP0MessageType, EHTTP0Exception> inConnection)
  {
    this.connection =
      Objects.requireNonNull(inConnection, "connection");
  }

  @Override
  public HBConnectionResultType<
    EHTTP0MessageType,
    EHTTP0ConnectionParameters,
    EHTTP0Exception>
  doConnect(
    final EHTTP0ConnectionParameters parameters)
  {
    return new HBConnectionError<>(new AlreadyConnectedException());
  }

  @Override
  public boolean isConnected()
  {
    return !this.connection.isClosed();
  }

  @Override
  public boolean isClosed()
  {
    return this.connection.isClosed();
  }

  @Override
  public void close()
    throws EHTTP0Exception
  {
    this.connection.close();
  }

  @Override
  public void doSend(
    final EHTTP0MessageType message)
    throws EHTTP0Exception
  {
    this.connection.send(message);
  }

  @Override
  public Optional<EHTTP0MessageType> doReceive(
    final Duration timeout)
    throws EHTTP0Exception
  {
    return this.connection.receive(timeout);
  }

  @Override
  public <R extends EHTTP0MessageType> R doAsk(
    final EHTTP0MessageType message)
    throws EHTTP0Exception, InterruptedException
  {
    return this.connection.ask(message);
  }
}

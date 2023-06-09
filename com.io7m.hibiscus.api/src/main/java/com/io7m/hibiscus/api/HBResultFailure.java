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

import java.util.function.Function;

/**
 * A failure result.
 *
 * @param result The result value
 * @param <S>    The type of success results
 * @param <F>    The type of failure results
 */

public record HBResultFailure<S, F>(F result)
  implements HBResultType<S, F>
{
  /**
   * Safely cast this value.
   *
   * @param <T> The success type
   *
   * @return This value assuming the success value was of type {@code T}
   */

  @SuppressWarnings("unchecked")
  public <T> HBResultFailure<T, F> cast()
  {
    return (HBResultFailure<T, F>) this;
  }

  @Override
  public boolean isSuccess()
  {
    return false;
  }

  @Override
  public <T> HBResultType<T, F> map(
    final Function<S, T> f)
  {
    return this.cast();
  }

  @Override
  public <T> HBResultType<T, F> flatMap(
    final Function<S, HBResultType<T, F>> f)
  {
    return this.cast();
  }

  @Override
  public <E extends Exception> S orElseThrow(
    final Function<F, E> f)
    throws E
  {
    throw f.apply(this.result);
  }
}

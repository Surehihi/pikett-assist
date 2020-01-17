package com.github.frimtec.android.pikettassist.domain;

import org.junit.Test;

import org.threeten.bp.Instant;
import java.util.function.Supplier;

import static com.github.frimtec.android.pikettassist.domain.Shift.TIME_TOLERANCE;
import static org.threeten.bp.Duration.ofMinutes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ShiftTest {

  @Test
  public void isOver() {
    Instant now = Shift.now();
    Shift shift = new Shift(0L, "Test", now, now.plus(ofMinutes(1)));
    checkTime(shift, () -> shift.isOver(now.minus(TIME_TOLERANCE).minusMillis(1)), false);
    checkTime(shift, () -> shift.isOver(now.minus(TIME_TOLERANCE)), false);
    checkTime(shift, () -> shift.isOver(now), false);
    checkTime(shift, () -> shift.isOver(now.plus(TIME_TOLERANCE).plus(ofMinutes(1))), false);
    checkTime(shift, () -> shift.isOver(now.plus(TIME_TOLERANCE).plus(ofMinutes(1)).plusMillis(1)), true);
  }

  @Test
  public void isInFuture() {
    Instant now = Shift.now();
    Shift shift = new Shift(0L, "Test", now, now.plus(ofMinutes(1)));
    checkTime(shift, () -> shift.isInFuture(now.minus(TIME_TOLERANCE).minusMillis(1)), true);
    checkTime(shift, () -> shift.isInFuture(now.minus(TIME_TOLERANCE)), false);
    checkTime(shift, () -> shift.isInFuture(now), false);
    checkTime(shift, () -> shift.isInFuture(now.plus(TIME_TOLERANCE).plus(ofMinutes(1))), false);
    checkTime(shift, () -> shift.isInFuture(now.plus(TIME_TOLERANCE).plus(ofMinutes(1)).plusMillis(1)), false);
  }

  @Test
  public void isNow() {
    Instant now = Shift.now();
    Shift shift = new Shift(0L, "Test", now, now.plus(ofMinutes(1)));
    checkTime(shift, () -> shift.isNow(now.minus(TIME_TOLERANCE).minusMillis(1)), false);
    checkTime(shift, () -> shift.isNow(now.minus(TIME_TOLERANCE)), true);
    checkTime(shift, () -> shift.isNow(now), true);
    checkTime(shift, () -> shift.isNow(now.plus(TIME_TOLERANCE).plus(ofMinutes(1))), true);
    checkTime(shift, () -> shift.isNow(now.plus(TIME_TOLERANCE).plus(ofMinutes(1)).plusMillis(1)), false);
  }

  private void checkTime(Shift shift, Supplier<Boolean> test, boolean expectedResult) {
    // act
    boolean result = test.get();

    // assert
    assertThat(result, is(expectedResult));
  }
}
package com.github.frimtec.android.pikettassist.ui.signal;

import android.content.Context;
import android.util.Pair;

import com.github.frimtec.android.pikettassist.R;
import com.github.frimtec.android.pikettassist.service.system.AlarmService;
import com.github.frimtec.android.pikettassist.service.system.SignalStrengthService;
import com.github.frimtec.android.pikettassist.state.ApplicationPreferences;
import com.github.frimtec.android.pikettassist.ui.common.AbstractAlarmActivity;

import org.threeten.bp.Duration;

import java.util.Collections;

import static com.github.frimtec.android.pikettassist.service.system.SignalStrengthService.isLowSignal;

public class LowSignalAlarmActivity extends AbstractAlarmActivity {

  private static final String TAG = "LowSignalAlarmActivity";

  public LowSignalAlarmActivity() {
    super(TAG, R.string.notification_low_signal_title, Pair.create(100, 500), SwipeButtonStyle.NO_SIGNAL);
    setEndCondition(() -> {
      SignalStrengthService.SignalLevel level = new SignalStrengthService(LowSignalAlarmActivity.this).getSignalStrength();
      return !isLowSignal(level, ApplicationPreferences.instance().getSuperviseSignalStrengthMinLevel(this)) || !ApplicationPreferences.instance().getSuperviseSignalStrength(getApplicationContext());
    }, Duration.ofSeconds(1));
  }

  public static void trigger(Context context, AlarmService alarmService) {
    AbstractAlarmActivity.trigger(LowSignalAlarmActivity.class, context, alarmService, Collections.emptyList());
  }

}
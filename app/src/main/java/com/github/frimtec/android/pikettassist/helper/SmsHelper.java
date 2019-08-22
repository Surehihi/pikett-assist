package com.github.frimtec.android.pikettassist.helper;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.github.frimtec.android.pikettassist.domain.Sms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class SmsHelper {

  private SmsHelper() {
  }

  public static List<Sms> getSmsFromIntent(Intent intent) {
    Bundle bundle = intent.getExtras();
    if (bundle != null) {
      Object[] pdus = (Object[]) bundle.get("pdus");
      return Arrays.stream(pdus)
          .map(pdu -> {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
            return new Sms(message.getOriginatingAddress(), message.getMessageBody());
          }).collect(Collectors.toList());
    }
    return Collections.emptyList();
  }


  public static void confirmSms(String confirmText, String number) {
    SmsManager smgr = SmsManager.getDefault();
    smgr.sendTextMessage(number, null, confirmText, null, null);
  }

}

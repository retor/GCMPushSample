package com.retor.gcmpushsample.test;

import android.os.Bundle;

import com.retor.gcmpushlibrary.services.GCMessageListenerBase;

/**
 * Created by retor on 03.10.2015.
 */
public class MesListener extends GCMessageListenerBase {
    @Override
    protected void initNotificationCreator() {
        setNotifyCreator(new NotificationCreatorSample(getApplicationContext()));
    }

    @Override
    protected void sendPush(String from, Bundle data) {
        String message = data.getString("message");
        getNotifyCreator().sendNotification(message);
    }
}

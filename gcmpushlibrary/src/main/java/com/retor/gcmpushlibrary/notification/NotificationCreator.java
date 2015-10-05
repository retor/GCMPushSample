package com.retor.gcmpushlibrary.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by retor on 03.10.2015.
 */
public abstract class NotificationCreator {

    private NotificationManager notificationManager;
    private int mId = 988;

    public NotificationCreator(Context context) {
        setNotificationManager((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
    }

    protected void setmId(int mId) {
        this.mId = mId;
    }

    protected void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    public void sendNotification(String message) {
        notificationManager.notify(mId, createNotification(message).build());
    }

    protected abstract NotificationCompat.Builder createNotification(String message);
}

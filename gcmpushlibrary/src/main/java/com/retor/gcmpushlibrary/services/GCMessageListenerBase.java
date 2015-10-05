package com.retor.gcmpushlibrary.services;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.retor.gcmpushlibrary.notification.NotificationCreator;

/**
 * Service listening on new GCM messages coming
 * and use NotificationCreatorSample class to create push nottification
 */
public abstract class GCMessageListenerBase extends GcmListenerService {
    private NotificationCreator notifyCreator;

    protected NotificationCreator getNotifyCreator() {
        return notifyCreator;
    }

    protected void setNotifyCreator(NotificationCreator notifyCreator) {
        this.notifyCreator = notifyCreator;
    }

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        if (getNotifyCreator() == null)
            initNotificationCreator();
        sendPush(from, data);
    }

    protected abstract void initNotificationCreator();

    protected abstract void sendPush(String from, Bundle data);
}

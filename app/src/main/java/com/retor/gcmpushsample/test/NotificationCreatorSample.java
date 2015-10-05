package com.retor.gcmpushsample.test;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.retor.gcmpushlibrary.notification.NotificationCreator;
import com.retor.gcmpushsample.R;
import com.retor.gcmpushsample.ui.DetailActivity;

/**
 * Class for create push notification
 * using in MessageListener service
 * Need to configure create push
 */
public class NotificationCreatorSample extends NotificationCreator {
    private Context context;

    public NotificationCreatorSample(Context context) {
        super(context);
        setmId(985);
        this.context = context;
    }

    private PendingIntent getPendingIntent(String message) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("msg", message);
        return PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
    }

    @Override
    protected NotificationCompat.Builder createNotification(String message) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Content Updated")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(getPendingIntent(message));
    }
}

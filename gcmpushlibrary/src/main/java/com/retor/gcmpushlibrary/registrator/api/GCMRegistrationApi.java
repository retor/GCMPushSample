package com.retor.gcmpushlibrary.registrator.api;

import android.content.Context;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Provides Observable's to register on GCM server
 * SENDER_ID id from google console
 */
public final class GCMRegistrationApi {
    private final String SENDER_ID;
    private Context context;
    private String[] TOPICS = {"global"};

    public GCMRegistrationApi(final Context context, String sender_id) {
        this.context = context;
        this.SENDER_ID = sender_id;
    }

    public void setTopics(String... topics){
        this.TOPICS = topics;
    }

    public Observable<String> registerInGCM() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                InstanceID instanceID = InstanceID.getInstance(context);
                try {
                    String token = instanceID.getToken(SENDER_ID,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    if (TOPICS!=null && TOPICS.length>0)
                        subscribeTopics(token);
                    subscriber.onNext(token);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> unregisterInGCM() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                InstanceID instanceID = InstanceID.getInstance(context);
                try {
                    instanceID.deleteToken(instanceID.getToken(SENDER_ID,
                                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                    instanceID.deleteInstanceID();
                    subscriber.onNext("success");
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(context);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
}

package com.retor.gcmpushlibrary.registrator;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;

import com.retor.gcmpushlibrary.registrator.api.GCMRegistrationApi;
import com.retor.gcmpushlibrary.utils.CheckUtil;
import com.retor.gcmpushlibrary.utils.GCMPreferencesUtils;

import rx.functions.Action1;


/**
 * Interface for create new Registrator
 */
public abstract class GCMRegistratorBase {
    private GCMRegistrationApi regApi;
    private CheckUtil checkUtil;
    private GCMPreferencesUtils preferencesUtils;
    private RegStateListener listener;

    public GCMRegistratorBase(Context context, String sender_id) {
        initFields(context, sender_id);
    }

    private void initFields(final Context context, String sender_id) {
        this.checkUtil = new CheckUtil(context);
        this.preferencesUtils = new GCMPreferencesUtils(PreferenceManager.getDefaultSharedPreferences(context));
        this.regApi = new GCMRegistrationApi(context, sender_id);
        setListener(new RegStateListener() {
            @Override
            public void onRegistrationSuccess(String s) {
                getPreferencesUtils().saveGoodToken(s);
                registerOnServer(s);
            }

            @Override
            public void onRegistrationFailed(final Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    protected GCMRegistrationApi getRegApi() {
        return regApi;
    }

    protected CheckUtil getCheckUtil() {
        return checkUtil;
    }

    protected GCMPreferencesUtils getPreferencesUtils() {
        return preferencesUtils;
    }

    protected RegStateListener getListener() {
        return listener;
    }

    public boolean checkGPlayServicesAvailable(Activity activity){
        return checkUtil.checkPlayServices(activity);
    }

    public void setListener(RegStateListener listener){
        this.listener = listener;
    }

    private void getSavedId() {
        String tmp = getPreferencesUtils().getCurrentToken();
        if (tmp == null)
            getListener().onRegistrationFailed(new NullPointerException("Can't load token"));
        else
            getListener().onRegistrationSuccess(tmp);
    }

    public void doCloudRegistration(){
        if (getPreferencesUtils().getCurrentState())
            getSavedId();
        else
            updateRegistration();
    }

    public void updateRegistration(){
        if (getCheckUtil().isNetworkAvailable()) {
            getPreferencesUtils().removeOldToken();
            getRegApi().registerInGCM().retry(3).subscribe(new Action1<String>() {
                @Override
                public void call(final String s) {
                    getListener().onRegistrationSuccess(s);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    getListener().onRegistrationFailed(throwable);
                }
            });
        }
    }

    public abstract void registerOnServer(String regId);
}

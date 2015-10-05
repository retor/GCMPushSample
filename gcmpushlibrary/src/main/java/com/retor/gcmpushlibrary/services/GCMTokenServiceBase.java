package com.retor.gcmpushlibrary.services;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.retor.gcmpushlibrary.registrator.GCMRegistratorBase;

/**
 * Service for renew token if last be compromised
 * from GCM manual
 */
public abstract class GCMTokenServiceBase extends InstanceIDListenerService {
    private GCMRegistratorBase registrator;

    public GCMRegistratorBase getRegistrator() {
        return registrator;
    }

    public void setRegistrator(GCMRegistratorBase registrator) {
        this.registrator = registrator;
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        if (getRegistrator() == null)
            initRegistrator();
        registrator.updateRegistration();
    }

    protected abstract void initRegistrator();
}

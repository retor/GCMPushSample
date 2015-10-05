package com.retor.gcmpushsample.test;

import android.content.Context;

import com.retor.gcmpushlibrary.registrator.GCMRegistratorBase;

/**
 * Implements Registrator interface for registration on GCM and app server's
 * Contains PreferencesUtils and CheckUtils
 * INNER_LISTENER implements RegStateListener and in standart save received token and do server request
 */
public class RegistratorInteractor extends GCMRegistratorBase {

    public RegistratorInteractor(final Context applicationContext) {
        super(applicationContext, "ID");
    }

    @Override
    public void registerOnServer(final String regId) {
        //TODO Create server registration request
    }
}

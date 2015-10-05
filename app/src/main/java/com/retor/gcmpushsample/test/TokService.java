package com.retor.gcmpushsample.test;


import com.retor.gcmpushlibrary.services.GCMTokenServiceBase;

/**
 * Created by retor on 03.10.2015.
 */
public class TokService extends GCMTokenServiceBase {
    @Override
    protected void initRegistrator() {
        setRegistrator(new RegistratorInteractor(getApplicationContext()));
    }
}

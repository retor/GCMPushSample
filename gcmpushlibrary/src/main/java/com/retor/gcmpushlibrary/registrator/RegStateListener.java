package com.retor.gcmpushlibrary.registrator;

/**
 * Callback Interface for listening registration status
 */
public interface RegStateListener {
    void onRegistrationSuccess(String id);
    void onRegistrationFailed(Throwable throwable);
}

package com.retor.gcmpushlibrary.utils;

import android.content.SharedPreferences;

/**
 * This class for working with SharedPreferences
 * saving, remove, return current token and current state
 * used in RegistrantInteractor
 */
public class GCMPreferencesUtils {
    private final String TOKEN_STATE = "token";
    private final String TOKEN_ID = "tokenId";
    private SharedPreferences sharedPreferences;

    public GCMPreferencesUtils(SharedPreferences preferences) {
        this.sharedPreferences = preferences;
    }

    public void saveGoodToken(String token) {
        sharedPreferences.edit().putString(TOKEN_ID, token).putBoolean(TOKEN_STATE, true).apply();
    }

    public void removeOldToken() {
        if (sharedPreferences.contains(TOKEN_ID) || sharedPreferences.contains(TOKEN_STATE))
            sharedPreferences.edit().remove(TOKEN_ID).remove(TOKEN_STATE).apply();
    }

    public String getCurrentToken() {
        return sharedPreferences.getString(TOKEN_ID, "");
    }

    public boolean getCurrentState() {
        return sharedPreferences.getBoolean(TOKEN_STATE, false);
    }
}

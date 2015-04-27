package com.hrules.eventcounter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

public class EventCounter {
    private final static String TAG = "EventCounter";

    private final static String DEFAULT_PREFERENCES_FILENAME = "EventCounter";
    public final static int DEFAULT_KEY_VALUE = 0;
    public final static int DEFAULT_INCREMENT_VALUE = 1;
    public final static int DEFAULT_DECREMENT_VALUE = 1;

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    private static EventCounter singleton;

    @SuppressLint("CommitPrefEdits")
    public EventCounter(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }

        preferences = context.getSharedPreferences(DEFAULT_PREFERENCES_FILENAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static EventCounter with(Context context) {
        if (singleton == null) {
            singleton = new EventCounter(context);
        }
        return singleton;
    }

    public EventCounter setKeyValue(String key, int value) {
        editor.putInt(key, value).commit();
        return this;
    }

    public int getKeyValue(String key) {
        return getKeyValue(key, DEFAULT_KEY_VALUE);
    }

    public int getKeyValue(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public void removeKey(String key) {
        editor.remove(key).commit();
    }

    public void addKey(String key) {
        resetKeyValue(key);
    }

    public void resetKeyValue(String key) {
        setKeyValue(key, DEFAULT_KEY_VALUE);
    }

    public void resetAll() {
        Map<String, ?> map = getAll();
        for (String key : map.keySet()) {
            resetKeyValue(key);
        }
    }

    public boolean checkKeyValue(String key, int expectedValue) {
        return getKeyValue(key, DEFAULT_KEY_VALUE) == expectedValue;
    }

    public EventCounter incrementKeyValue(String key) {
        return incrementKeyValue(key, DEFAULT_INCREMENT_VALUE);
    }

    public EventCounter incrementKeyValue(String key, int incrementBy) {
        int currentValue = getKeyValue(key);
        long newValue = (long) currentValue + incrementBy;
        if (newValue > Integer.MAX_VALUE) {
            newValue = Integer.MAX_VALUE;
        }
        setKeyValue(key, (int) newValue);
        return this;
    }

    public EventCounter decrementKeyValue(String key) {
        return decrementKeyValue(key, DEFAULT_DECREMENT_VALUE);
    }

    public EventCounter decrementKeyValue(String key, int decrementBy) {
        int currentValue = getKeyValue(key);
        long newValue = (long) currentValue - decrementBy;
        if (newValue < 0) {
            newValue = 0;
        }
        setKeyValue(key, (int) newValue);
        return this;
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public void removeAll() {
        editor.clear().commit();
    }

    public boolean contains(String key) {
        return preferences.contains(key);
    }

    public SharedPreferences getSharedPreferences() {
        return preferences;
    }
}
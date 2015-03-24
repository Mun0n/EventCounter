package com.hrules.eventcounter.demo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import com.hrules.eventcounter.EventCounter;


public class MainActivity extends ActionBarActivity implements SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener {
    private final static String TAG = "MainActivity";

    private final static String EVENTCOUNTER_COUNTER = "EVENTCOUNTER_COUNTER";
    private final static String EVENTCOUNTER_ACTIVITY = "EVENTCOUNTER_ACTIVITY";

    private EventCounter eventCounter;
    private TextView tvCount;
    private TextView tvActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventCounter = new EventCounter(this);

        tvCount = (TextView) findViewById(R.id.count);
        tvActivity = (TextView) findViewById(R.id.activity);

        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonReset).setOnClickListener(this);

        setTextCount();
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventCounter.getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvActivity.setText(String.format(getString(R.string.activity), eventCounter.incrementKeyValue(EVENTCOUNTER_ACTIVITY).getKeyValue(EVENTCOUNTER_ACTIVITY)));

        eventCounter.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(EVENTCOUNTER_COUNTER)) {
            setTextCount();
        }
    }

    private void setTextCount() {
        tvCount.setText(String.format(getString(R.string.count), eventCounter.getKeyValue(EVENTCOUNTER_COUNTER)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAdd:
                eventCounter.incrementKeyValue(EVENTCOUNTER_COUNTER);
                break;

            case R.id.buttonReset:
                eventCounter.resetKeyValue(EVENTCOUNTER_COUNTER);
                break;
        }
    }
}

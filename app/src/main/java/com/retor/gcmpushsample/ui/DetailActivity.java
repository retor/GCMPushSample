package com.retor.gcmpushsample.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.retor.gcmpushsample.R;

/**
 * Created by retor on 21.09.2015.
 */
public class DetailActivity extends FragmentActivity {

    TextView msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        msg = (TextView)findViewById(R.id.msg);
        if (getIntent() != null && getIntent().hasExtra("msg"))
            msg.setText(getIntent().getStringExtra("msg"));
    }
}
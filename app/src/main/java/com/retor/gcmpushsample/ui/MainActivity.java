package com.retor.gcmpushsample.ui;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.retor.gcmpushlibrary.registrator.GCMRegistratorBase;
import com.retor.gcmpushlibrary.registrator.RegStateListener;
import com.retor.gcmpushsample.R;
import com.retor.gcmpushsample.test.RegistratorInteractor;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements RegStateListener {

    private Button send;
    private TextView textView;
    private GCMRegistratorBase registrant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        doRegistration();
    }

    /**
    *This method need migrate to Activity. Or if no need to check GPServices can start registration in Application.onCreate()
    **/
    private void doRegistration() {
        registrant = new RegistratorInteractor(getApplicationContext());
        registrant.setListener(this);
        if (registrant.checkGPlayServicesAvailable(this)) {
            registrant.doCloudRegistration();
        }
    }

    private void initViews() {
        this.textView = (TextView) findViewById(R.id.textView);
        this.send = (Button) findViewById(R.id.button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /**
    *This is test message send method
     * SENDER_KEY from google console
     * **/
    private void sendMessage() {
        final String SENDER_KEY = "KEY";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Prepare JSON containing the GCM message content. What to send and where to send.
                    JSONObject jGcmData = new JSONObject();
                    JSONObject jData = new JSONObject();
                    try {
                        jData.put("message", "This message is only for Test");
                        jGcmData.put("to", "/topics/global");
                        jGcmData.put("data", jData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Create connection to send GCM Message request.
                    URL url = new URL("https://android.googleapis.com/gcm/send");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Authorization", "key=" + SENDER_KEY);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    // Send GCM message content.
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(jGcmData.toString().getBytes());
                    // Read GCM response.
                    InputStream inputStream = conn.getInputStream();
                    String resp = IOUtils.toString(inputStream);
                    Log.d("Response Send", resp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onRegistrationSuccess(String id) {
        textView.setText("Registered");
        send.setEnabled(true);
    }

    @Override
    public void onRegistrationFailed(final Throwable throwable) {
        new AlertDialog.Builder(this).setTitle("Error").setMessage(throwable.getLocalizedMessage()).setCancelable(true).create().show();
    }
}

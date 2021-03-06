package com.example.kaleb.minecraft;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private Socket mSocket;
    private List<String> myData = new ArrayList<String>();
    private MainActivity mActivity = this;

    private GoogleApiClient mClient;
    private ArrayList<String> strArray;

    {
        try {
            mSocket = IO.socket("http://45.55.94.5:3000/");
        } catch (URISyntaxException e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MAIN ACTIVITY", "start");
        strArray = new ArrayList<String>();
        mSocket.on("log", onNewMessage);
        mSocket.connect();
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this since content won't change layout size
        mRecyclerView.setHasFixedSize(true);

        // needs a layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // needs and adapter
        mAdapter = new RecyclerAdapter(strArray);
        mRecyclerView.setAdapter(mAdapter);

        final TextView meditTextView = (TextView)findViewById(R.id.editText4);
        Typeface tf = Typeface.createFromAsset(meditTextView.getContext().getAssets(), "fonts/font-mine.ttf");
        meditTextView.setTypeface(tf);
        meditTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (meditTextView.getText().toString().trim().length() == 0) {
                    return false;
                }
                emitText(meditTextView);
                return false;
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    public void emitText(TextView tv) {
        String emitValue = "say " + tv.getText();
        Log.d("MAIN ACTIVITY", emitValue);
        tv.setText("");
        mSocket.emit("command", emitValue);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("log", onNewMessage);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String message = (String) args[0];
                    // add the message to view
                    mAdapter.updateSocketInfo(message);
                }
            });
        }
    };
}

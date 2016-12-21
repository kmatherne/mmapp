package com.example.kaleb.minecraft;

import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Socket mSocket;
    private List<String> myData = new ArrayList<String>();
    private MainActivity mActivity = this;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient mClient;
    private ArrayList<String> strArray;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MAIN ACTIVITY", "start");
        {
            try {
                mSocket = IO.socket("http://45.55.94.5:3000/");
            } catch (URISyntaxException e) {
            }
        }
        strArray = new ArrayList<String>();
        strArray.add("None");
        mSocket.on("log", onNewMessage);
        mSocket.connect();
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this since content won't change layout size
        mRecyclerView.setHasFixedSize(true);

        // needs a layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
//                1);
//        Canvas mCanvas = new Canvas();
//        mCanvas.drawARGB(60, 91, 95, 102);
//        mDividerItemDecoration.setDrawable(getDrawable(R.drawable.textspacers));
//        mRecyclerView.draw(mCanvas);
//        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        // needs and adapter
        mAdapter = new RecyclerAdapter(strArray);
        mRecyclerView.setAdapter(mAdapter);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


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
            Log.d("MAIN ACTIVITY", "Inside the socket call");
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("MAIN ACTIVITY", "Print message");
                    String message = (String) args[0];
                    // add the message to view
                    Log.d("MAIN ACTIVITY", message);
                    mAdapter.updateSocketInfo(message);
                }
            });
        }
    };
}

package org.cssac.karyovirtualassistantforautistickids;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DrumActivity extends AppCompatActivity {
    MediaPlayer bongoLow[], bongoMid[], bongoHigh[];
    ImageView lowView, midView, highView;
    int low, mid, high;
    public static int MEDIA_COUNT = 3;

    Dialog loadScreenDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drum);

        lowView = (ImageView) findViewById(R.id.drumLow);
        midView = (ImageView) findViewById(R.id.drumMid);
        highView = (ImageView) findViewById(R.id.drumHigh);
        low = 0;
        mid = 0;
        high = 0;

        bongoLow = new MediaPlayer[MEDIA_COUNT];
        bongoMid = new MediaPlayer[MEDIA_COUNT];
        bongoHigh = new MediaPlayer[MEDIA_COUNT];
        for (int i = 0; i < MEDIA_COUNT; i++) {
            if (bongoLow[i] != null){
                if (bongoLow[i].isPlaying()||bongoLow[i].isLooping()) {
                    bongoLow[i].stop();
                }
                bongoLow[i].release();
                bongoLow[i] = null;
            }
            bongoLow[i] = MediaPlayer.create(this, R.raw.bongolow);

            if (bongoMid[i] != null){
                if (bongoMid[i].isPlaying()||bongoMid[i].isLooping()) {
                    bongoMid[i].stop();
                }
                bongoMid[i].release();
                bongoMid[i] = null;
            }
            bongoMid[i] = MediaPlayer.create(this, R.raw.bongomid);

            if (bongoHigh[i] != null){
                if (bongoHigh[i].isPlaying()||bongoHigh[i].isLooping()) {
                    bongoHigh[i].stop();
                }
                bongoHigh[i].release();
                bongoHigh[i] = null;
            }
            bongoHigh[i] = MediaPlayer.create(this, R.raw.bongohigh);
        }

        lowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bongoLow[low].start();
                low = ( low + 1 ) % MEDIA_COUNT;
            }
        });
        midView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bongoMid[mid].start();
                mid = ( mid + 1 ) % MEDIA_COUNT;
            }
        });
        highView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bongoHigh[high].start();
                high = ( high + 1 ) % MEDIA_COUNT;
            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoadScreen();
            }
        }, 15000);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<MEDIA_COUNT;i++) {
                    if (bongoLow[i]!=null) {
                        bongoLow[i].stop();
                        bongoLow[i].reset();
                        bongoLow[i].release();
                        bongoLow[i] = null;
                    }
                    if (bongoMid[i]!=null) {
                        bongoMid[i].stop();
                        bongoMid[i].reset();
                        bongoMid[i].release();
                        bongoMid[i] = null;
                    }
                    if (bongoHigh[i]!=null) {
                        bongoHigh[i].stop();
                        bongoHigh[i].reset();
                        bongoHigh[i].release();
                        bongoHigh[i] = null;
                    }
                }
                loadScreenDialog.dismiss();
                finish();
                startActivity(new Intent(getApplicationContext(), LearningAppHomeActivity.class));
            }
        }, 18000);
    }

    public void showLoadScreen() {
        View view = getLayoutInflater().inflate(R.layout.load_game_screen, null);
        loadScreenDialog = new Dialog(this, R.style.MyTheme);
        loadScreenDialog.setContentView(view);
        loadScreenDialog.setCancelable(false);
        TextView text = (TextView) view.findViewById(R.id.load_message);
        text.setText("Loading Home...");
        loadScreenDialog.show();
    }
}

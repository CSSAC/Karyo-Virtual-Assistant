package org.cssac.karyovirtualassistantforautistickids;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.cssac.karyovirtualassistantforautistickids.utils.BubbleAdapter;

import pl.droidsonroids.gif.GifImageView;

public class BubblePopActivity extends AppCompatActivity {

    int score = 0;
    MediaPlayer bubble_pop;

    GifImageView gif;

    Dialog loadScreenDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_pop);

        bubble_pop = MediaPlayer.create(this, R.raw.bubble_pop);

        gif = (GifImageView) findViewById(R.id.gif);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        BubbleAdapter bubbleAdapter = new BubbleAdapter(this);
        gridView.setAdapter(bubbleAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, final View view, int position, long id) {
                if (view.getVisibility()==View.INVISIBLE) return;
                bubble_pop.start();
                view.setVisibility(View.INVISIBLE);
                score++;
                if (score%7==0) gif.setImageResource(R.drawable.smile_jump_x3);
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

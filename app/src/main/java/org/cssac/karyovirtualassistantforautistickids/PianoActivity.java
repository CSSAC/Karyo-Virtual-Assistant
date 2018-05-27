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

public class PianoActivity extends AppCompatActivity {

    private static int MEDIA_COUNT = 3;

    MediaPlayer aNote[], aSharpNote[], bNote[], cNote[], cSharpNote[], dNote[], dSharpNote[], eNote[], fNote[], fSharpNote[], gNote[];
    ImageView aNoteView, aSharpNoteView, bNoteView, cNoteView, cSharpNoteView, dNoteView, dSharpNoteView, eNoteView, fNoteView, fSharpNoteView, gNoteView;
    int aNoteViewIdx, aSharpNoteViewIdx, bNoteViewIdx, cNoteViewIdx, cSharpNoteViewIdx, dNoteViewIdx, dSharpNoteViewIdx, eNoteViewIdx, fNoteViewIdx, fSharpNoteViewIdx, gNoteViewIdx;

    Dialog loadScreenDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        aNote = new MediaPlayer[MEDIA_COUNT];
        aSharpNote = new MediaPlayer[MEDIA_COUNT];
        bNote = new MediaPlayer[MEDIA_COUNT];
        cNote = new MediaPlayer[MEDIA_COUNT];
        cSharpNote = new MediaPlayer[MEDIA_COUNT];
        dNote = new MediaPlayer[MEDIA_COUNT];
        dSharpNote = new MediaPlayer[MEDIA_COUNT];
        eNote = new MediaPlayer[MEDIA_COUNT];
        fNote = new MediaPlayer[MEDIA_COUNT];
        fSharpNote = new MediaPlayer[MEDIA_COUNT];
        gNote = new MediaPlayer[MEDIA_COUNT];

        aNoteView = (ImageView) findViewById(R.id.aNote);
        aSharpNoteView = (ImageView) findViewById(R.id.aSharpNote);
        bNoteView = (ImageView) findViewById(R.id.bNote);
        cNoteView = (ImageView) findViewById(R.id.cNote);
        cSharpNoteView = (ImageView) findViewById(R.id.cSharpNote);
        dNoteView = (ImageView) findViewById(R.id.dNote);
        dSharpNoteView = (ImageView) findViewById(R.id.dSharpNote);
        eNoteView = (ImageView) findViewById(R.id.eNote);
        fNoteView = (ImageView) findViewById(R.id.fNote);
        fSharpNoteView = (ImageView) findViewById(R.id.fSharpNote);
        gNoteView = (ImageView) findViewById(R.id.gNote);

        for (int i=0;i<MEDIA_COUNT;i++) {
            aNote[i] = MediaPlayer.create(this, R.raw.a);
            aSharpNote[i] = MediaPlayer.create(this, R.raw.asharp);
            bNote[i] = MediaPlayer.create(this, R.raw.b);
            cNote[i] = MediaPlayer.create(this, R.raw.c);
            cSharpNote[i] = MediaPlayer.create(this, R.raw.csharp);
            dNote[i] = MediaPlayer.create(this, R.raw.d);
            dSharpNote[i] = MediaPlayer.create(this, R.raw.dsharp);
            eNote[i] = MediaPlayer.create(this, R.raw.e);
            fNote[i] = MediaPlayer.create(this, R.raw.f);
            fSharpNote[i] = MediaPlayer.create(this, R.raw.fsharp);
            gNote[i] = MediaPlayer.create(this, R.raw.g);
        }

        aNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aNote[aNoteViewIdx++].start();
                aNoteViewIdx%=MEDIA_COUNT;
            }
        });
        aSharpNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aSharpNote[aSharpNoteViewIdx++].start();
                aSharpNoteViewIdx%=MEDIA_COUNT;
            }
        });
        bNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bNote[bNoteViewIdx++].start();
                bNoteViewIdx%=MEDIA_COUNT;
            }
        });
        cNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cNote[cNoteViewIdx++].start();
                cNoteViewIdx%=MEDIA_COUNT;
            }
        });
        cSharpNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cSharpNote[cSharpNoteViewIdx++].start();
                cSharpNoteViewIdx%=MEDIA_COUNT;
            }
        });
        dNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dNote[dNoteViewIdx++].start();
                dNoteViewIdx%=MEDIA_COUNT;
            }
        });
        dSharpNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dSharpNote[dSharpNoteViewIdx++].start();
                dSharpNoteViewIdx%=MEDIA_COUNT;
            }
        });
        eNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eNote[eNoteViewIdx++].start();
                eNoteViewIdx%=MEDIA_COUNT;
            }
        });
        fNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fNote[fNoteViewIdx++].start();
                fNoteViewIdx%=MEDIA_COUNT;
            }
        });
        fSharpNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fSharpNote[fSharpNoteViewIdx++].start();
                fSharpNoteViewIdx%=MEDIA_COUNT;
            }
        });
        gNoteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gNote[gNoteViewIdx++].start();
                gNoteViewIdx%=MEDIA_COUNT;
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

package org.cssac.karyovirtualassistantforautistickids;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.cssac.karyovirtualassistantforautistickids.models.MCQProblem;
import org.cssac.karyovirtualassistantforautistickids.models.UserInformation;
import org.cssac.karyovirtualassistantforautistickids.utils.TextToSpeechModule;

import java.util.List;


public class MCQGameActivity extends AppCompatActivity {
    private static final String DRAWABLE = "drawable/";
    private static final String EMPTY_STRING = "";
    private static final String LIST_MCQ = "LIST_MCQ";
    private static final String USER_INFORMATION = "USER_INFORMATION";
    private static final String ID_OPTION_1 = "idOption1";
    private static final String ID_OPTION_2 = "idOption2";
    private static final String ID_OPTION_3 = "idOption3";
    private static final int LOAD_SCREEN_DELAY = 5000;
    private static final int SPEECH_DELAY = 0;
    private static final int SET_OPTIONS_DELAY = 1000;
    private static final int RETRY_SCREEN_DELAY = 1000;
    private static final int REWARD_SCREEN_DELAY = 3000;

    UserInformation userInformation;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    TextView idStatement;
    ImageView idOption1, idOption2, idOption3;
    private Drawable drawable;
    Dialog loadScreenDialog, retryScreenDialog, levelUpScreenDialog;

    List<MCQProblem> mcqProblemList;
    int mcqCounter;
    String correctImage;
    int level;
    String tag;

    Animation animationZoomIn;
    Animation animationZoomOut;
    TextToSpeechModule ttsm;

    int mcqAttempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqgame);

        // Dialog Show
        // Load data
        // post delay - close dialog

        showLoadScreen();

        idStatement = (TextView) findViewById(R.id.idStatement);
        idOption1 = (ImageView) findViewById(R.id.idOption1);
        idOption2 = (ImageView) findViewById(R.id.idOption2);
        idOption3 = (ImageView) findViewById(R.id.idOption3);
        animationZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        animationZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        mcqProblemList = (List<MCQProblem>) intent.getSerializableExtra(LIST_MCQ);
        userInformation = (UserInformation) intent.getSerializableExtra(USER_INFORMATION);
        tag = mcqProblemList.get(0).getTag();
        level = mcqProblemList.get(0).getLevelDifficulty();

        speak(EMPTY_STRING);
        mcqCounter = 0;

        idOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ID", "idOption1");
                idOption1.startAnimation(animationZoomIn);
                idOption1.startAnimation(animationZoomOut);

                idStatement.setText(correctImage + "idOption1");
                if (correctImage==ID_OPTION_1) correctAttempt();
                else wrongAttempt();
            }
        });

        idOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ID", "idOption2");
                idOption2.startAnimation(animationZoomIn);
                idOption2.startAnimation(animationZoomOut);

                idStatement.setText(correctImage + "idOption2");
                if (correctImage==ID_OPTION_2) correctAttempt();
                else wrongAttempt();
            }
        });

        idOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ID", "idOption3");
                idOption3.startAnimation(animationZoomIn);
                idOption3.startAnimation(animationZoomOut);

                idStatement.setText(correctImage + "idOption3");
                if (correctImage==ID_OPTION_3) correctAttempt();
                else wrongAttempt();
            }
        });

        setOptions();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadScreenDialog.dismiss();
                speakAfterDelay();
                Log.i("USER INFORMATION", userInformation.firstName);
            }
        }, LOAD_SCREEN_DELAY);
    }

    public void promptCorrectImage() {
        if (correctImage.equals(ID_OPTION_1)) {
            idOption1.startAnimation(animationZoomIn);
            idOption1.startAnimation(animationZoomOut);
        }
        else if (correctImage.equals(ID_OPTION_2)) {
            idOption2.startAnimation(animationZoomIn);
            idOption2.startAnimation(animationZoomOut);
        }
        else {
            idOption3.startAnimation(animationZoomIn);
            idOption3.startAnimation(animationZoomOut);
        }
    }

    public void revealCorrectImage() {
        if (correctImage.equals(ID_OPTION_1)) {
            idOption1.startAnimation(animationZoomIn);
            idOption1.startAnimation(animationZoomOut);
        }
        else if (correctImage.equals(ID_OPTION_2)) {
            idOption2.startAnimation(animationZoomIn);
            idOption2.startAnimation(animationZoomOut);
        }
        else {
            idOption3.startAnimation(animationZoomIn);
            idOption3.startAnimation(animationZoomOut);
        }
    }

    public void wrongAttempt() {
        mcqAttempt++;
        if (level==1 || mcqAttempt==2) {
            revealCorrectImage();
            nextMCQ();
        }
        else {
            promptCorrectImage();
        }
    }

    public void correctAttempt() {
        // Reinforcement
        revealCorrectImage();
        nextMCQ();
    }

    public void showLoadScreen() {
        View view = getLayoutInflater().inflate(R.layout.load_game_screen, null);
        loadScreenDialog = new Dialog(this, R.style.MyTheme);
        loadScreenDialog.setContentView(view);
        loadScreenDialog.setCancelable(false);
        loadScreenDialog.show();
    }

    public void showRetryScreen() {
        View view = getLayoutInflater().inflate(R.layout.retry_mcq, null);
        retryScreenDialog = new Dialog(this, R.style.MyTheme);
        retryScreenDialog.setContentView(view);
        retryScreenDialog.setCancelable(false);
        retryScreenDialog.show();
    }

    public void showLevelUpScreen() {
        View view = getLayoutInflater().inflate(R.layout.level_up_reward, null);
        levelUpScreenDialog = new Dialog(this, R.style.MyTheme);
        levelUpScreenDialog.setContentView(view);
        levelUpScreenDialog.setCancelable(false);
        levelUpScreenDialog.show();
    }

    public void speak(String text) {
        ttsm = new TextToSpeechModule(text, this);
    }

    public void speakAfterDelay() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                speak(mcqProblemList.get(mcqCounter).getStatement());
                if (mcqCounter!=mcqProblemList.size()) mcqCounter++;
            }
        }, SPEECH_DELAY);
    }

    public void setOptions() {
        String options[] = mcqProblemList.get(mcqCounter).getOptions();
        String correctAns = mcqProblemList.get(mcqCounter).getCorrectAnswer();
        idStatement.setText(mcqProblemList.get(mcqCounter).getStatement() + correctAns);

        int dr = getResources().getIdentifier(DRAWABLE + options[0], null, getPackageName());
        drawable = getResources().getDrawable(dr);
        idOption1.setImageDrawable(drawable);

        dr = getResources().getIdentifier(DRAWABLE + options[1], null, getPackageName());
        drawable = getResources().getDrawable(dr);
        idOption2.setImageDrawable(drawable);

        dr = getResources().getIdentifier(DRAWABLE + options[2], null, getPackageName());
        drawable = getResources().getDrawable(dr);
        idOption3.setImageDrawable(drawable);

        if (options[0].equals(correctAns)) {
            correctImage = ID_OPTION_1;
        }
        else if (options[1].equals(correctAns)) {
            correctImage = ID_OPTION_2;
        }
        else if (options[2].equals(correctAns)){
            correctImage = ID_OPTION_3;
        }

        mcqAttempt = 0;

        idStatement.setText(correctAns + correctImage + " " + options[0] + options[1] + options[2]);
    }

    public void setOptionsAfterDelay() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setOptions();
                speakAfterDelay();
            }
        }, SET_OPTIONS_DELAY);
    }

    public void nextMCQ() {
        Log.i("TAG", "NEXT MCQ");
        if (mcqCounter<mcqProblemList.size()) {
            Log.i("IN RANGE", Integer.toString(mcqCounter));
            setOptionsAfterDelay();
        }
        else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showRetryScreen();
//                    levelUp();
                }
            }, RETRY_SCREEN_DELAY);
        }
    }

    // to-do
    public void levelUp() {
        saveUserInformation();
        showLevelUpScreen();
//        ImageView idReward = (ImageView) findViewById(R.id.idReward);
//        int dr = getResources().getIdentifier(DRAWABLE + "apple", null, getPackageName());
//        drawable = getResources().getDrawable(dr);
//        idReward.setImageDrawable(drawable);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                levelUpScreenDialog.dismiss();
                finish();
            }
        }, REWARD_SCREEN_DELAY);
    }

    public void retryLevel(View v) {
        mcqCounter = 0;
        setOptions();
        saveUserInformation();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // SAVE GAME DATA
                retryScreenDialog.dismiss();
                speakAfterDelay();
            }
        }, SET_OPTIONS_DELAY);
    }

    public void saveUserInformation() {
        // SAVE GAME DATA
        showLoadScreen();

        databaseReference.child(firebaseUser.getUid()).setValue(userInformation)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        loadScreenDialog.dismiss();
                    }
                });
    }

    public void backToMainMenu(View v) {
        saveUserInformation();
        finish();
        startActivity(new Intent(this, LearningAppHomeActivity.class));
    }
}

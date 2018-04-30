package org.cssac.karyovirtualassistantforautistickids;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.cssac.karyovirtualassistantforautistickids.models.MCQProblem;
import org.cssac.karyovirtualassistantforautistickids.models.UserInformation;
import org.cssac.karyovirtualassistantforautistickids.utils.TextToSpeechModule;
import org.cssac.karyovirtualassistantforautistickids.utils.BounceInterpolator;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

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

    private static final int LEVEL_UP_THRESHOLD = 80;

    Boolean controlFrozen;

    UserInformation userInformation;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    ImageView idOption1, idOption2, idOption3;
    ImageView prompter1, prompter2, prompter3;
    private Drawable drawable;
    Dialog loadScreenDialog, retryScreenDialog, levelUpScreenDialog;
    GifImageView gif;

    List<MCQProblem> mcqProblemList;
    int mcqCounter;
    String correctImage;
    int level;
    String tag;
    int numberCorrect, totalAttempts;

    Animation animationZoomIn;
    Animation animationZoomOut;
    Animation animationZoomBounce;
    Animation animationBounce;
    Animation animationBlink;
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

        idOption1 = (ImageView) findViewById(R.id.idOption1);
        idOption2 = (ImageView) findViewById(R.id.idOption2);
        idOption3 = (ImageView) findViewById(R.id.idOption3);
        prompter1 = (ImageView) findViewById(R.id.prompter1);
        prompter2 = (ImageView) findViewById(R.id.prompter2);
        prompter3 = (ImageView) findViewById(R.id.prompter3);
        prompter1.setVisibility(View.INVISIBLE);
        prompter2.setVisibility(View.INVISIBLE);
        prompter3.setVisibility(View.INVISIBLE);
        gif = (GifImageView) findViewById(R.id.gif);

        controlFrozen = false;

        animationZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        animationZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        animationBlink = AnimationUtils.loadAnimation(this, R.anim.blink_prompt);
        animationBounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20.0);
        animationBounce.setInterpolator(interpolator);
        animationZoomBounce = AnimationUtils.loadAnimation(this, R.anim.zoom_bounce);
        animationZoomBounce.setInterpolator(interpolator);

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
        numberCorrect = 0;
        totalAttempts = 0;

        idOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ID", "idOption1");

                if (controlFrozen==false) {
//                    idOption1.startAnimation(animationBounce);

                    totalAttempts++;

                    if (correctImage==ID_OPTION_1) correctAttempt();
                    else wrongAttempt();
                }
            }
        });

        idOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ID", "idOption2");

                if (controlFrozen==false) {
//                    idOption2.startAnimation(animationBounce);

                    totalAttempts++;

                    if (correctImage == ID_OPTION_2) correctAttempt();
                    else wrongAttempt();
                }
            }
        });

        idOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ID", "idOption3");

                if (controlFrozen==false) {
//                    idOption3.startAnimation(animationBounce);

                    totalAttempts++;

                    if (correctImage == ID_OPTION_3) correctAttempt();
                    else wrongAttempt();
                }
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

    public void revealCorrectImage() {
        if (correctImage.equals(ID_OPTION_1)) {
            idOption1.startAnimation(animationZoomBounce);
        }
        else if (correctImage.equals(ID_OPTION_2)) {
            idOption2.startAnimation(animationZoomBounce);
        }
        else {
            idOption3.startAnimation(animationZoomBounce);
        }
    }

    public void showPrompter() {
        if (correctImage.equals(ID_OPTION_1)) {
            prompter1.startAnimation(animationBlink);
        }
        else if (correctImage.equals(ID_OPTION_2)) {
            prompter2.startAnimation(animationBlink);
        }
        else {
            prompter3.startAnimation(animationBlink);
        }
    }

    public void wrongAttempt() {
        mcqAttempt++;
        if (level==1 || mcqAttempt==2) {
            revealCorrectImage();
            nextMCQ();
        }
        else {
            showPrompter();
        }
    }

    public void correctAttempt() {
        // Reinforcement
        if (mcqCounter%2==1) gif.setImageResource(R.drawable.thumbs_up_gif);
        else gif.setImageResource(R.drawable.jump_gif);
        numberCorrect++;
        revealCorrectImage();
        nextMCQ();
    }

    public void showLoadScreen() {
        View view = getLayoutInflater().inflate(R.layout.load_game_screen, null);
        loadScreenDialog = new Dialog(this, R.style.MyTheme);
        loadScreenDialog.setContentView(view);
        loadScreenDialog.setCancelable(false);
        TextView text = (TextView) view.findViewById(R.id.load_message);
        text.setText("Loading Game Data...");
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
                if (level==1) showPrompter();
                if (mcqCounter!=mcqProblemList.size()) mcqCounter++;
                controlFrozen = false;
            }
        }, SPEECH_DELAY);
    }

    public void setOptions() {
        String options[] = mcqProblemList.get(mcqCounter).getOptions();
        String correctAns = mcqProblemList.get(mcqCounter).getCorrectAnswer();

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
        controlFrozen = true;
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
                    userInformation.correctAttempts.put(tag, userInformation.correctAttempts.get(tag)+numberCorrect);
                    userInformation.attempts.put(tag, userInformation.attempts.get(tag)+totalAttempts);
                    int per = (numberCorrect*100)/totalAttempts;

                    Log.i("Percentange", Integer.toString(per));
                    if (per >= LEVEL_UP_THRESHOLD) {
                        levelUp();
                    }
                    else {
                        showRetryScreen();
                    }
                }
            }, RETRY_SCREEN_DELAY);
        }
    }

    // to-do
    public void levelUp() {
        Log.i("Level Up", "UPDATE");
        if (level<userInformation.maxLevel.get(tag)) {
            userInformation.level.put(tag, level+1);
        }
        else {
            // highest level done
            // restart from level 1
            userInformation.level.put(tag, 1);
        }

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

    public void retryLevel(View view) {
        mcqCounter = 0;
        numberCorrect = 0;
        totalAttempts = 0;

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

    public void backToMainMenu(View view) {
        saveUserInformation();
        finish();
        startActivity(new Intent(this, LearningAppHomeActivity.class));
    }

    @Override
    public void onBackPressed() {

    }
}

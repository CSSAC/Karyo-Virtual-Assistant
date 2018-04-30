package org.cssac.karyovirtualassistantforautistickids;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.cssac.karyovirtualassistantforautistickids.constants.Tags;
import org.cssac.karyovirtualassistantforautistickids.databaseHandlers.MCQHandler;
import org.cssac.karyovirtualassistantforautistickids.models.MCQProblem;
import org.cssac.karyovirtualassistantforautistickids.models.UserInformation;
import org.cssac.karyovirtualassistantforautistickids.utils.BounceInterpolator;
import org.cssac.karyovirtualassistantforautistickids.utils.TagsAdapter;

import java.io.Serializable;
import java.util.List;

public class LearningAppHomeActivity extends AppCompatActivity {

    private static final String LIST_MCQ = "LIST_MCQ";
    private static final String USER_INFORMATION = "USER_INFORMATION";
    private static final int BOUNCE_DELAY = 500;

    boolean backPressed = false;

    MCQHandler mcqHandler;
    Dialog loadScreenDialog;
    UserInformation userInformation;
    Intent intent;

    Animation animationBounce;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_app_home);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.butterfly_music);

        animationBounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20.0);
        animationBounce.setInterpolator(interpolator);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        ImageView settingsButton = (ImageView) findViewById(R.id.settingsButton);
        ImageView analyticsButton = (ImageView) findViewById(R.id.analyticsButton);
        TagsAdapter tagsAdapter = new TagsAdapter(this);
        gridView.setAdapter(tagsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, final View view, int position, long id) {
                final String tag = Tags.TAGS[position];
                final Handler handler = new Handler();
                view.startAnimation(animationBounce);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<MCQProblem> mcqProblemList = mcqHandler.getMCQByTagAndLevel(tag, userInformation.level.get(tag));
                        intent.putExtra(LIST_MCQ, (Serializable) mcqProblemList);
                        mediaPlayer.stop();
                        finish();
                        startActivity(intent);

                    }
                }, BOUNCE_DELAY);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSettingsActivity();
            }
        });

        analyticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAnalyticsActivity();
            }
        });

        mcqHandler = new MCQHandler(this);

        intent = new Intent(this, MCQGameActivity.class);

        fetchUserInformation();
        showLoadScreen();

//        MCQProblem mcqProblem = new MCQProblem();
//        mcqProblem.setId(2);
//        mcqProblem.setStatement("select green");
//        String options[] = {"green_splatter", "black_splatter", "white_splatter"};
//        mcqProblem.setOptions(options);
//        mcqProblem.setCorrectAnswer("green_splatter");
//        mcqProblem.setTag("colour");
//        mcqProblem.setLevelDifficulty(1);
//        mcqHandler.insertMCQ(mcqProblem);
    }

    public void showLoadScreen() {
        View view = getLayoutInflater().inflate(R.layout.load_game_screen, null);
        loadScreenDialog = new Dialog(this, R.style.MyTheme);
        loadScreenDialog.setContentView(view);
        loadScreenDialog.setCancelable(false);
        TextView text = (TextView) view.findViewById(R.id.load_message);
        text.setText("Logging In...Make sure you're connected to Internet");
        loadScreenDialog.show();
    }

    public void fetchUserInformation() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInformation = dataSnapshot.getValue(UserInformation.class);
                intent.putExtra(USER_INFORMATION, (Serializable) userInformation);
                Log.i("NAME", userInformation.firstName);
                loadScreenDialog.dismiss();
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                finish();
                startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
            }
        });
    }

    public void toAnalyticsActivity() {
        Intent intent = new Intent(this, AnalyticsActivity.class);
        intent.putExtra(USER_INFORMATION, (Serializable) userInformation);
        mediaPlayer.stop();
        finish();
        startActivity(intent);
    }

    public void toSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(USER_INFORMATION, (Serializable) userInformation);
        mediaPlayer.stop();
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (backPressed) {
            finish();
            System.exit(0);
        }
        backPressed = true;
        Toast.makeText(this, "Press BACK again to Exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressed = false;
            }
        }, 2000);
    }
}

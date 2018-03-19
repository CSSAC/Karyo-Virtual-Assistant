package org.cssac.karyovirtualassistantforautistickids;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.cssac.karyovirtualassistantforautistickids.databaseHandlers.MCQHandler;
import org.cssac.karyovirtualassistantforautistickids.models.MCQProblem;
import org.cssac.karyovirtualassistantforautistickids.models.UserInformation;

import java.io.Serializable;
import java.util.List;

public class LearningAppHomeActivity extends AppCompatActivity {

    private static final String LIST_MCQ = "LIST_MCQ";
    private static final String USER_INFORMATION = "USER_INFORMATION";

    MCQHandler mcqHandler;
    Dialog loadScreenDialog;
    UserInformation userInformation;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_app_home);

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                finish();
                startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
            }
        });
    }

    public void toMCQGameActivity(View view) {
        List<MCQProblem> mcqProblemList = mcqHandler.getAllMCQ();
        TextView textView = (TextView) findViewById(R.id.textView);
        Log.i("Statement", mcqProblemList.get(0).getStatement());
        textView.setText(Integer.toString(mcqProblemList.size()));
        Log.i("Size", Integer.toString(mcqProblemList.size()));

        intent.putExtra(LIST_MCQ, (Serializable) mcqProblemList);
//        intent.putExtra(USER_INFORMATION, (Serializable) userInformation);
        startActivity(intent);
    }
}

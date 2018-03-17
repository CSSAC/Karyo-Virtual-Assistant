package org.cssac.karyovirtualassistantforautistickids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.cssac.karyovirtualassistantforautistickids.databaseHandlers.MCQHandler;
import org.cssac.karyovirtualassistantforautistickids.models.MCQProblem;

import java.io.Serializable;
import java.util.List;

public class LearningAppHomeActivity extends AppCompatActivity {
    MCQHandler mcqHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_app_home);

        mcqHandler = new MCQHandler(this);

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

    public void toMCQGameActivity(View view) {
        List<MCQProblem> mcqProblemList = mcqHandler.getAllMCQ();
        TextView textView = (TextView) findViewById(R.id.textView);
        Log.i("Statement", mcqProblemList.get(0).getStatement());
        textView.setText(Integer.toString(mcqProblemList.size()));
        Log.i("Size", Integer.toString(mcqProblemList.size()));

        Intent intent = new Intent(this, MCQGameActivity.class);
        intent.putExtra("LIST_MCQ", (Serializable) mcqProblemList);
        startActivity(intent);
    }
}

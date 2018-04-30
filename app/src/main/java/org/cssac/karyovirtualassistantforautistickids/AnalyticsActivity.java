package org.cssac.karyovirtualassistantforautistickids;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.cssac.karyovirtualassistantforautistickids.constants.Tags;
import org.cssac.karyovirtualassistantforautistickids.models.UserInformation;
import org.cssac.karyovirtualassistantforautistickids.utils.AnalyticsTagsAdapter;
import org.cssac.karyovirtualassistantforautistickids.utils.TagsAdapter;

public class AnalyticsActivity extends AppCompatActivity {
    private static final String USER_INFORMATION = "USER_INFORMATION";

    UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        userInformation = (UserInformation) getIntent().getSerializableExtra(USER_INFORMATION);

        ListView listView = (ListView) findViewById(R.id.listView);
        AnalyticsTagsAdapter analyticsTagsAdapter= new AnalyticsTagsAdapter(this, userInformation);
        listView.setAdapter(analyticsTagsAdapter);

        int levelsSolved = 0, totalLevels = 0;
        int correctAttempts = 0, totalAttempts = 0;

        for (int i = 0;i< Tags.TAGS.length;i++) {
            totalLevels+=userInformation.maxLevel.get(Tags.TAGS[i]);
            levelsSolved+=(userInformation.level.get(Tags.TAGS[i])-1);

            totalAttempts+=userInformation.attempts.get(Tags.TAGS[i]);
            correctAttempts+=userInformation.correctAttempts.get(Tags.TAGS[i]);
        }


        TextView questionsSolved = (TextView) findViewById(R.id.questionsSolved);
        String questionsSolvedText = String.format("%d / %d\nQuestions Solved", levelsSolved*10, totalLevels*10);
        questionsSolved.setText(questionsSolvedText);

        TextView levelsSolvedView = (TextView) findViewById(R.id.levelsSolved);
        String levelsSolvedText = String.format("%d / %d\nLevels Solved", levelsSolved, totalLevels);
        levelsSolvedView.setText(levelsSolvedText);

        TextView correctAttemptsView = (TextView) findViewById(R.id.correctAttempts);
        String correctAttemptsText = String.format("%d / %d\nCorrect Attempts", correctAttempts, totalAttempts);
        correctAttemptsView.setText(correctAttemptsText);

        TextView accuracyView = (TextView) findViewById(R.id.accuracy);
        int acc = totalAttempts==0?0:(correctAttempts*100)/totalAttempts;
        String accuracyText = String.format("%d Percent\nAccuracy", acc);
        accuracyView.setText(accuracyText);

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), LearningAppHomeActivity.class));
    }
}
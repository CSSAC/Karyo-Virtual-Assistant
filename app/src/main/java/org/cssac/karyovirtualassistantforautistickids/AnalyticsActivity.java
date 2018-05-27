package org.cssac.karyovirtualassistantforautistickids;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.cssac.karyovirtualassistantforautistickids.constants.Tags;
import org.cssac.karyovirtualassistantforautistickids.models.UserInformation;
import org.cssac.karyovirtualassistantforautistickids.utils.AnalyticsTagsAdapter;

import java.io.Serializable;

public class AnalyticsActivity extends AppCompatActivity {
    private static final String USER_INFORMATION = "USER_INFORMATION";

    Dialog analyticsScreen;
    String selectedTag;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedTag = Tags.TAGS[position];
                showGraphAnalytics();
            }
        });

    }

    public void showGraphAnalytics() {
        Intent intent = new Intent(this, GraphViewAnalytics.class);
        intent.putExtra("ANALYTICS_LIST", (Serializable) userInformation.correctList.get(selectedTag));
        intent.putExtra("TITLE", (Serializable) selectedTag);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), LearningAppHomeActivity.class));
    }
}

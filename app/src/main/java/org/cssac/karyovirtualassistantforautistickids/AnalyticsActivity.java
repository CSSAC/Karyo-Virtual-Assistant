package org.cssac.karyovirtualassistantforautistickids;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

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


    }
}

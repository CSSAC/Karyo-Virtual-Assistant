package org.cssac.karyovirtualassistantforautistickids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class AssistantActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void userLogOut(View view) {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
    }
}

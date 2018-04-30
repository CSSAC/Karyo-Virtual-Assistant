package org.cssac.karyovirtualassistantforautistickids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.cssac.karyovirtualassistantforautistickids.models.UserInformation;

public class SettingsActivity extends AppCompatActivity {
    private static final String USER_INFORMATION = "USER_INFORMATION";

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        userInformation = (UserInformation) intent.getSerializableExtra(USER_INFORMATION);
    }

    public void resetGame(View view) {
        userInformation = new UserInformation(userInformation.firstName, userInformation.lastName, userInformation.dobDate, userInformation.dobMonth, userInformation.dobYear);
        databaseReference.child(firebaseUser.getUid()).setValue(userInformation);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, UserLoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), LearningAppHomeActivity.class));
    }
}

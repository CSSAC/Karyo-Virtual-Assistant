package org.cssac.karyovirtualassistantforautistickids;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.cssac.karyovirtualassistantforautistickids.models.UserInformation;

import java.io.Serializable;

public class UserLoginActivity extends AppCompatActivity {

    private static final String EMPTY_ENTRIES = "Please fill all the Entries";
    private static final String INCORRECT_CREDENTIALS = "Incorrect Email or Password";

    private EditText idEmail;
    private EditText idPassword;
    private ImageView login_btn;
    private TextView registerText;
    private ProgressBar spinner;

    private String email;
    private String password;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        idEmail = (EditText) findViewById(R.id.idEmail);
        idPassword = (EditText) findViewById(R.id.idPassword);

        login_btn = (ImageView) findViewById(R.id.login_btn);
        registerText = (TextView) findViewById(R.id.registerText);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogIn();
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterActivity();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null) {
            finish();
            startActivity(new Intent(getApplicationContext(), LearningAppHomeActivity.class));
        }
    }

    public void userLogIn() {
        email = idEmail.getText().toString();
        password = idPassword.getText().toString();

        // Any Empty EditText
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, EMPTY_ENTRIES, Toast.LENGTH_SHORT).show();
        }
        // No Empty EditText
        else {
            spinner.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                spinner.setVisibility(View.GONE);
                                Toast.makeText(UserLoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                // To new Activity
                                Intent intent = new Intent(getApplicationContext(), LearningAppHomeActivity.class);
                                finish();
                                startActivity(intent);
                            }
                            else {
                                spinner.setVisibility(View.GONE);
                                Toast.makeText(UserLoginActivity.this, INCORRECT_CREDENTIALS, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void toRegisterActivity() {
        finish();
        startActivity(new Intent(getApplicationContext(), UserRegisterActivity.class));
    }
}

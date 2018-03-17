package org.cssac.karyovirtualassistantforautistickids;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.cssac.karyovirtualassistantforautistickids.models.UserInformation;


public class UserRegisterActivity extends AppCompatActivity {

    private static final String EMPTY_ENTRIES = "Please fill all the Entries";
    private static final String EMAIL_REGISTRATION_ERROR = "Incorrect Email or Already registered";
    private static final String REGISTRATION_SUCCESS = "User Successfully Registered and Added";

    private String email;
    private String password;
    private String firstName, lastName;
    private String date, month, year;

    private EditText idEmail;
    private EditText idPassword;
    private EditText idFirstName, idLastName;
    private EditText idDate, idMonth, idYear;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        idEmail = (EditText) findViewById(R.id.idEmail);
        idPassword = (EditText) findViewById(R.id.idPassword);
        idFirstName = (EditText) findViewById(R.id.idFirstName);
        idLastName = (EditText) findViewById(R.id.idLastName);
        idDate = (EditText) findViewById(R.id.idDate);
        idMonth = (EditText) findViewById(R.id.idMonth);
        idYear = (EditText) findViewById(R.id.idYear);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null) {
            finish();
            startActivity(new Intent(getApplicationContext(), AssistantActivity.class));
        }
    }

    public void userRegister(View view) {
        email = idEmail.getText().toString();
        password = idPassword.getText().toString();
        firstName = idFirstName.getText().toString();
        lastName = idLastName.getText().toString();
        date = idDate.getText().toString();
        month = idMonth.getText().toString();
        year = idYear.getText().toString();

        // Any Empty EditText
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)
                || TextUtils.isEmpty(date) || TextUtils.isEmpty(month)|| TextUtils.isEmpty(year)) {
            Toast.makeText(this, EMPTY_ENTRIES, Toast.LENGTH_SHORT).show();
        }
        // No Empty EditText
        else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                UserInformation userInformation = new UserInformation(firstName, lastName, Integer.parseInt(date), Integer.parseInt(month), Integer.parseInt(year));
                                // Login with registered Email
                                firebaseAuth.signInWithEmailAndPassword(email, password);

                                firebaseUser = firebaseAuth.getCurrentUser();
                                // Add User to Firebase Database
                                databaseReference = FirebaseDatabase.getInstance().getReference();
                                databaseReference.child(firebaseUser.getUid()).setValue(userInformation);
                                Toast.makeText(UserRegisterActivity.this, REGISTRATION_SUCCESS, Toast.LENGTH_SHORT).show();

                                // To new Activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), AssistantActivity.class));
                            }
                            else {
                                // Incorrect or Already registered
                                Toast.makeText(UserRegisterActivity.this, EMAIL_REGISTRATION_ERROR, Toast.LENGTH_SHORT).show();
                                Log.i("Registration Failed", EMAIL_REGISTRATION_ERROR);
                            }
                        }
                    });
        }
    }

    public void toLoginActivity(View view) {
        finish();
        startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
    }

}

package com.example.skyex;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPage extends AppCompatActivity {

     private EditText etFname,etLname,etEmail,etPassword;
     private FirebaseAuth mAuth;
     private Button btnRegisterSignup;
     private ProgressBar progressBar;
     private static final String TAG = "SignupPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        //initialise variable
        etFname = findViewById(R.id.etFname);
        etLname = findViewById(R.id.etLname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        //switch to login activity
        TextView tvLogin= findViewById(R.id.tvLoginLink);
        tvLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent loginActivity = new Intent(SignupPage.this,LoginPage.class);
                startActivity(loginActivity);
            }
        });

        btnRegisterSignup = findViewById(R.id.btnRegisterSignup);
        btnRegisterSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String textfirstname = etFname.getText().toString();
                String textlastname = etLname.getText().toString();
                String textemail = etEmail.getText().toString();
                String textpassword = etPassword.getText().toString();


                //check if fields are empty
                if (TextUtils.isEmpty(textfirstname)) {
                    Toast.makeText(SignupPage.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                    etFname.requestFocus();
                } else if (TextUtils.isEmpty(textlastname)) {
                    Toast.makeText(SignupPage.this, "Please enter last name", Toast.LENGTH_SHORT).show();
                    etLname.requestFocus();
                } else if (TextUtils.isEmpty(textemail)) {
                    Toast.makeText(SignupPage.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textemail).matches()) {
                    Toast.makeText(SignupPage.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                } else if (TextUtils.isEmpty(textpassword)) {
                    Toast.makeText(SignupPage.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                }else if (textpassword.length()<6) {
                    Toast.makeText(SignupPage.this, "Password is too short!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(textemail,textpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FirebaseUser firebaseUser = mAuth.getCurrentUser();

                                //enter user data into DB
                                User readWriteUserDetails = new User(textfirstname,textlastname,textemail,textpassword);

                                DatabaseReference refprofile = FirebaseDatabase.getInstance().getReference("Registered User");
                                refprofile.child(firebaseUser.getUid()).setValue(readWriteUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            //send email verification
                                            firebaseUser.sendEmailVerification();

                                            Toast.makeText(SignupPage.this, "Registration success!Email verification has been sent!",Toast.LENGTH_SHORT).show();

                                            //redirect to login activity
                                            Intent loginactivity = new Intent(SignupPage.this,LoginPage.class);
                                               loginactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | loginactivity.FLAG_ACTIVITY_CLEAR_TASK| loginactivity.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(loginactivity);
                                            finish();
                                        }else{
                                            Toast.makeText(SignupPage.this, "Registration failed!",Toast.LENGTH_SHORT).show();

                                        }
                                        progressBar.setVisibility(View.GONE);

                                    }
                                });

                            }else{
                               try{
                                   throw task.getException();
                               }catch(FirebaseAuthUserCollisionException e) {
                                   etEmail.setError("This email is already registered.Please use another");
                                   etEmail.requestFocus();
                                } catch (Exception e) {
                                   Log.e(TAG,e.getMessage());
                                   Toast.makeText(SignupPage.this,e.getMessage(),Toast.LENGTH_LONG).show();
                               }
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });



    }


}
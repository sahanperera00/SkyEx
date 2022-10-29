package com.example.skyex;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    private EditText etLoginEmail, etLoginPw;
    private Button buttonLogin;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPw = findViewById(R.id.etLoginPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();

        //switch to register user  activity
        TextView tvRegister = findViewById(R.id.tvRegisterLink);
        tvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent registerActivity = new Intent(LoginPage.this,SignupPage.class);
                startActivity(registerActivity);
            }
        });

        //Login user

         buttonLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String textEmail= etLoginEmail.getText().toString();
                 String textPassword = etLoginPw.getText().toString();

                 //check if fields r empty
                 if (TextUtils.isEmpty(textEmail)) {
                     Toast.makeText(LoginPage.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                     etLoginEmail.requestFocus();
                 }
                 else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                     Toast.makeText(LoginPage.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                     etLoginEmail.requestFocus();
                 }
                 else if (TextUtils.isEmpty(textPassword)) {
                     Toast.makeText(LoginPage.this, "Please enter password", Toast.LENGTH_SHORT).show();
                     etLoginPw.requestFocus();
                 }else{
                     progressBar.setVisibility(View.VISIBLE);
                     mAuth.signInWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){


                                 //get instance of current user
//                                 FirebaseUser fbUser = mAuth.getCurrentUser();

                                 //check if user is verified before giving access to login
//                                 if(fbUser.isEmailVerified()){
                                     Toast.makeText(LoginPage.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                     Intent profileactivity = new Intent(LoginPage.this,UserProfilePage.class);
                                     startActivity(profileactivity);
//                                 }else {
//                                     fbUser.sendEmailVerification();
//                                     displayAlert();
//                                 }

                             }else{
                                 Toast.makeText(LoginPage.this, "Login fail.Please try again!",Toast.LENGTH_SHORT).show();
                             }
                             progressBar.setVisibility(View.GONE);
                         }
                     });
                 }
             }
         });
    }

//    private void displayAlert() {
//        AlertDialog.Builder adb = new AlertDialog.Builder(LoginPage.this);
//        adb.setTitle("Email verification failed");
//        adb.setMessage("Please verify email in order to login");
//
//        //open email app
//        adb.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent email = new Intent(Intent.ACTION_MAIN);
//                email.addCategory(email.CATEGORY_APP_EMAIL);
//                email.addFlags(email.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(email);
//            }
//        });
//
//        AlertDialog alertMessage = adb.create();
//        alertMessage.show();
//
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            Toast.makeText(LoginPage.this, "User already logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginPage.this,UserProfilePage.class));
            finish();
        }
    }
}
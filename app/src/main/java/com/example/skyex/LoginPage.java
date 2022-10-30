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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {

    private EditText etLoginEmail, etLoginPw;
    private Button buttonLogin;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    User readWriteUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPw = findViewById(R.id.etLoginPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar2);
        firebaseAuth = FirebaseAuth.getInstance();
        readWriteUserDetails = getIntent().getParcelableExtra("readWriteUserDetails");
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        //switch to forgot pw
        TextView tvresetpw = findViewById(R.id.tvResetPw);
        tvresetpw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent resetActivity = new Intent(LoginPage.this,ForgotPasswordActivity.class);
                startActivity(resetActivity);
            }
        });

        //switch to register user  activity
        TextView tvRegister = findViewById(R.id.tvRegisterLink);
        tvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent registerActivity = new Intent(LoginPage.this,SignupPage.class);
                startActivity(registerActivity);
            }
        });

         buttonLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String textEmail= etLoginEmail.getText().toString();
                 String textPassword = etLoginPw.getText().toString();

                 if (TextUtils.isEmpty(textEmail)) {
                     Toast.makeText(LoginPage.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                     etLoginEmail.requestFocus();
                 }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                     Toast.makeText(LoginPage.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                     etLoginEmail.requestFocus();
                 }else if (TextUtils.isEmpty(textPassword)) {
                     Toast.makeText(LoginPage.this, "Please enter password", Toast.LENGTH_SHORT).show();
                     etLoginPw.requestFocus();
                 }else {
//                     progressBar.setVisibility(View.VISIBLE);
                     firebaseAuth.signInWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 if(firebaseUser!=null){
                                     if(firebaseUser.isEmailVerified()){
                                         DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered User");
                                         databaseReference.child(firebaseUser.getUid()).setValue(readWriteUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {
                                                 Toast.makeText(LoginPage.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                                 Intent profileactivity = new Intent(LoginPage.this,ShopActivity.class);
                                                 startActivity(profileactivity);
                                             }
                                         });
                                     }else{
                                         Toast.makeText(LoginPage.this, "Email not verified", Toast.LENGTH_SHORT).show();
                                     }
                                }
                             }else {
                                 Toast.makeText(LoginPage.this, "Login fail.Please try again!",Toast.LENGTH_SHORT).show();
                             }
                         }
//                             progressBar.setVisibility(View.GONE);
//                         }
                     });
                 }
             }
         });
    }
}
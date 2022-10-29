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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnResetPw;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private final static String TAG = "ForgotPasswordPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmailPwReset);
        btnResetPw = findViewById(R.id.btnResetPw);
        progressBar = findViewById(R.id.progressBar3);

        btnResetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();

                //check if fields r empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter registered email", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    resetPassword(email);
                }
            }
        });
    }

    private void resetPassword(String email){
        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Password reset link sent!", Toast.LENGTH_SHORT).show();
                    Intent forgotpwActivity = new Intent(ForgotPasswordActivity.this,LoginPage.class);
                    forgotpwActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | forgotpwActivity.FLAG_ACTIVITY_CLEAR_TASK| forgotpwActivity.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(forgotpwActivity);
                    finish();
                }else {
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        etEmail.setError("User doesn't exist");
                    }catch(Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}

package com.example.skyex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteProfileActivity extends AppCompatActivity {

    private EditText etauthpw,etreason;
    private TextView tvAuth;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    private String authPwd,reasonForDel;
    private Button btnAuth,btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_profile_page);

        progressBar = findViewById(R.id.progressBar4);
        etreason = findViewById(R.id.etReason);
        etauthpw = findViewById(R.id.etAuthPw);
        tvAuth = findViewById(R.id.etAuthPw);
        btnAuth  = findViewById(R.id.btnAuth);
        btnDelete = findViewById(R.id.btnDelete);

        //disable delete btn until authenticated
        btnDelete.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser.equals("")){
            Toast.makeText(DeleteProfileActivity.this, "Error!Something went wrong", Toast.LENGTH_SHORT).show();
            Intent deleteActivity = new Intent(DeleteProfileActivity.this,UserProfilePage.class);
            deleteActivity .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | deleteActivity .FLAG_ACTIVITY_CLEAR_TASK| deleteActivity .FLAG_ACTIVITY_NEW_TASK);
            startActivity(deleteActivity );
            finish();
        }else{
            reAuthenticateUser(firebaseUser);
        }
    }

    private void reAuthenticateUser(FirebaseUser firebaseUser) {

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authPwd = etauthpw.getText().toString();
                reasonForDel = etreason.getText().toString();

                if(TextUtils.isEmpty(authPwd)){
                    Toast.makeText(DeleteProfileActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    etauthpw.requestFocus();
                }else if(TextUtils.isEmpty(reasonForDel)){
                    Toast.makeText(DeleteProfileActivity.this, "Please give a reason for deletion", Toast.LENGTH_SHORT).show();
                    etreason.requestFocus();
                }else{
                    progressBar.setVisibility((View.VISIBLE));

                    //re-auth user
                    AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),authPwd);

                    firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);

                                //disable auth btn and enable delete button
                                etauthpw.setEnabled(false);
                                etreason.setEnabled(false);
                                btnAuth.setEnabled(false);
                                btnDelete.setEnabled(true);

                                tvAuth.setText("You are now authenticated!");
                                Toast.makeText(DeleteProfileActivity.this, "You can delete account now", Toast.LENGTH_SHORT).show();

                                //update color of delete btn
                                btnDelete.setBackgroundTintList(ContextCompat.getColorStateList(
                                        DeleteProfileActivity.this, R.color.black));

                                btnDelete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        showConfirmationAlert();
                                    }
                                });

                            }else{
                                try{
                                    throw task.getException();
                                }catch(Exception e){
                                    Toast.makeText(DeleteProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void showConfirmationAlert() {

        AlertDialog.Builder adb = new AlertDialog.Builder(DeleteProfileActivity.this);
        adb.setTitle("Delete Account");
        adb.setMessage("Are you sure you want to delete account? This action is irreversible");

        //open email app
        adb.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                deleteUser(firebaseUser);
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent cancelActivity = new Intent(DeleteProfileActivity.this,UserDashboardActivity.class);
                startActivity(cancelActivity);
                finish();
            }
        });

        AlertDialog alertMessage = adb.create();
        alertMessage.show();

    }

    private void deleteUser(FirebaseUser firebaseUser) {

    }
}
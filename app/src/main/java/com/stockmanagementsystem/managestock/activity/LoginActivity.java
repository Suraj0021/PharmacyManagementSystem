package com.stockmanagementsystem.managestock.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stockmanagementsystem.managestock.MainActivity;
import com.stockmanagementsystem.managestock.R;
import com.stockmanagementsystem.managestock.modelCLasses.UserModel;

public class LoginActivity extends AppCompatActivity {
    TextView btn, rstPass;
    EditText inputEmail, inputPassword;
    Button btnLogin;
    private FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn = findViewById(R.id.textviewSignup);
        rstPass = findViewById(R.id.forgotpassword);
        inputEmail = findViewById(R.id.inputUser);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnRst);
        btnLogin.setOnClickListener(view -> checkups());

        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(LoginActivity.this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        rstPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void checkups() {

        boolean data = true;

        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString();
        if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Email is not Valid!");
            data = false;
        }
        if (password.isEmpty() || password.length() < 6) {
            showError(inputPassword, "At least 6 character");
            data = false;
        }
        if (data) {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Please wait while processing");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            String firebaseValidEmail = email.replace('.', '?')
                    .replace('$', '%');
            FirebaseDatabase.getInstance()
                    .getReference("suspendList")
                    .child(firebaseValidEmail)
                    .addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        mLoadingBar.dismiss();
                                        activityStarter();
                                        Toast.makeText(LoginActivity.this, "Successfully logged in.", Toast.LENGTH_LONG).show();
                                    } else {
                                        mLoadingBar.dismiss();
                                        inputEmail.setError("Email Not Fount");
                                        Toast.makeText(LoginActivity.this, "Email not found", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (NullPointerException e) {

                                inputEmail.setError("Email Not Fount");

                                Toast.makeText(LoginActivity.this, "Email not found", Toast.LENGTH_LONG).show();
                                mLoadingBar.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }
    }

    private void activityStarter() {
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String UID = mUser.getUid();//uid refers to user id
        FirebaseDatabase
                .getInstance()
                .getReference("Users")
                .child(UID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel user = snapshot.getValue(UserModel.class);
                        SharedPreferences sharedPref = getSharedPreferences("User", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        editor.apply();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}
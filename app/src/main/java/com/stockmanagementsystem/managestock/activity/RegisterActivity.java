package com.stockmanagementsystem.managestock.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.stockmanagementsystem.managestock.R;
import com.stockmanagementsystem.managestock.modelCLasses.SaveUserInfo;
import com.stockmanagementsystem.managestock.modelCLasses.UserInfo;
import com.stockmanagementsystem.managestock.modelCLasses.UserPreferences;

public class RegisterActivity extends AppCompatActivity {
    TextView btn;
    private EditText inputUsername, inputPassword, inputEmail, inputConfirmPassword, inputPhone, inputAddress;
    Button btnRegister;

    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btn = findViewById(R.id.AlreadyHaveAccount);
        inputUsername = findViewById(R.id.inputUser);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        inputPhone = findViewById(R.id.inputPhone);
        inputAddress = findViewById(R.id.inputAddress);

        mAuth = FirebaseAuth.getInstance();
        // FirebaseUser mUser=mAuth.getCurrentUser();

        mLoadingBar = new ProgressDialog(RegisterActivity.this);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCrededentials();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });
    }

    private void checkCrededentials() {
        boolean data = true;

        String username = inputUsername.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString();
        String pNumber = inputPhone.getText().toString().trim();
        String address = inputAddress.getText().toString().trim();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if (username.isEmpty()) {
            data = false;
            showError(inputUsername, "Name cannot be empty!");
        }
        if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Email is not Valid!");
            data = false;
        }
        if (pNumber.isEmpty()) {
            showError(inputPhone, "Contact cannot be empty!");
            data = false;
        }
        if (address.isEmpty()) {
            showError(inputAddress, "Address cannot be empty!");
            data = false;
        }
        if (password.isEmpty() || password.length() < 6) {
            showError(inputPassword, "At least 6 characters long");
            data = false;

        }
        if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            showError(inputConfirmPassword, "Password not matched!");
            data = false;
        }
        if (data) {
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("please wait");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();
                        UserInfo user = new UserInfo(address, email, username, Long.parseLong(pNumber));
                        FirebaseDatabase.getInstance().getReference("UsersInfo")
                                .child(uid)
                                .setValue(user);

                        Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();

                        mLoadingBar.dismiss();


                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {

                                Toast.makeText(RegisterActivity.this, "Successfully logged in.", Toast.LENGTH_LONG).show();

                                UserPreferences userPrefs = new UserPreferences(getApplicationContext());
                                userPrefs.saveUserData(username, uid, email);
                                String username = userPrefs.getUsername();
                                String uid2 = userPrefs.getUid();
                                String email = userPrefs.getEmail();

                                finish();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                addNewUserInfo();
                            }
                        });

                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void addNewUserInfo() {
        SaveUserInfo user = new SaveUserInfo();
        user.uploadData(inputUsername.getText().toString(), inputPhone.getText().toString(), inputEmail.getText().toString(),
                inputAddress.getText().toString());
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}
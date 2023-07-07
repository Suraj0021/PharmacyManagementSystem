package com.stockmanagementsystem.managestock.modelCLasses;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SaveUserInfo {
    String uid = "";
    private FirebaseFirestore db;

    public SaveUserInfo() {
        db = FirebaseFirestore.getInstance();
    }

    public void uploadData(String name,  String phone,String email, String address) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();

            Map<String, Object> data = new HashMap<>();
            data.put("name", name);
            data.put("email", email);
            data.put("phone", phone);
            data.put("address", address);

            db.collection("UserInfo").document(uid).collection("data")
                    .add(data)
                    .addOnSuccessListener(documentReference -> {
                        // Data uploaded successfully
                    })
                    .addOnFailureListener(e -> {
                        // Failed to upload data
                    });

            db.collection("UserInformation").document(uid).collection("data")
                    .add(data)
                    .addOnSuccessListener(documentReference -> {
                        // Data uploaded successfully
                    })
                    .addOnFailureListener(e -> {
                        // Failed to upload data
                    });


        }
    }
}

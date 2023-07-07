package com.stockmanagementsystem.managestock.modelCLasses;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class GetBills {

    private FirebaseFirestore db;

    public GetBills() {
        db = FirebaseFirestore.getInstance();
    }

    public void readData() {

        db.collection("medicines")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String name = document.getString("name");
                                int age = document.getLong("age").intValue();
                                String email = document.getString("email");
                                String phone = document.getString("phone");
                                String medicine = document.getString("medicine");
                                String manufactureDate = document.getString("manufacture_date");
                                String expireDate = document.getString("expire_date");
                                int quantity = document.getLong("quantity").intValue();
                                double price = document.getDouble("price");
                                String doctorName = document.getString("doctor_name");
                                String date = document.getString("today");
                            }
                        }
                    }
                });
    }
}
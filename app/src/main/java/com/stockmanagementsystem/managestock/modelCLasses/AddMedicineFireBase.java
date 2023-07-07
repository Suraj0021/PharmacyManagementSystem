package com.stockmanagementsystem.managestock.modelCLasses;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stockmanagementsystem.managestock.activity.AddMedicineActivity;

import java.util.HashMap;
import java.util.Map;

public class AddMedicineFireBase {
    private DatabaseReference db;
    String uid = "";

    public void uploadMedicineData(String medicine, String company, String manufactureDate, String expireDate, int quantity, double price) {



        db = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();

            Map<String, Object> data = new HashMap<>();
            data.put("medicine", medicine);
            data.put("company", company);
            data.put("manufacture_date", manufactureDate);
            data.put("expire_date", expireDate);
            data.put("quantity", quantity);
            data.put("price", price);

            String medicineId = db.child("MedicineList").child(uid).push().getKey();
            db.child("MedicineList").child(uid).child(medicineId).setValue(data)
                    .addOnSuccessListener(aVoid -> {

                        Log.d("MedicineListId",medicineId);

                    })
                    .addOnFailureListener(e -> {

                    });
        }

    }



}
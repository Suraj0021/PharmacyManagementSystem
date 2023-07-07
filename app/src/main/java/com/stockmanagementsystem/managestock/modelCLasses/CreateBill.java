package com.stockmanagementsystem.managestock.modelCLasses;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateBill {
    String uid = "";
    private FirebaseFirestore db;

    public CreateBill() {

    }

//    public void uploadData(String name, int age, String email, String phone,
//                           String medicine, String manufactureDate, String expireDate,
//                           int quantity, double price, String doctorName,String today) {
//        db = FirebaseFirestore.getInstance();
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser != null) {
//             uid = currentUser.getUid();
//
//            Map<String, Object> data = new HashMap<>();
//            data.put("name", name);
//            data.put("age", age);
//            data.put("email", email);
//            data.put("phone", phone);
//            data.put("medicine", medicine);
//            data.put("manufacture_date", manufactureDate);
//            data.put("expire_date", expireDate);
//            data.put("quantity", quantity);
//            data.put("price", price);
//            data.put("doctor_name", doctorName);
//            data.put("today", today);
//
//            db.collection("bill").document(uid).collection(getTodayDate())
//                    .add(data)
//                    .addOnSuccessListener(documentReference -> {
//                        // Data uploaded successfully
//                    })
//                    .addOnFailureListener(e -> {
//                        // Failed to upload data
//                    });
//
//            db.collection("sales").document(uid).collection("data")
//                    .add(data)
//                    .addOnSuccessListener(documentReference -> {
//                        // Data uploaded successfully
//                    })
//                    .addOnFailureListener(e -> {
//                        // Failed to upload data
//                    });
//
//            db.collection("Customers").document(uid).collection("data")
//                    .add(data)
//                    .addOnSuccessListener(documentReference -> {
//                        // Data uploaded successfully
//                    })
//                    .addOnFailureListener(e -> {
//                        // Failed to upload data
//                    });
//
//
//        } else {
//            // No user is currently signed in
//        }

 public void uploadData(String name, String age, String email, String phone,
                            ArrayList<String> medicines, String price, String doctorName, String today) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();

            Map<String, Object> data = new HashMap<>();
            data.put("name", name);
            data.put("age", age);
            data.put("email", email);
            data.put("phone", phone);
            data.put("medicines", medicines);
            data.put("price", price);
            data.put("doctor_name", doctorName);
            data.put("today", today);

            db.collection("bill").document(uid).collection(getTodayDate())
                    .add(data)
                    .addOnSuccessListener(documentReference -> {
                        // Data uploaded successfully
                    })
                    .addOnFailureListener(e -> {
                        // Failed to upload data
                    });

            db.collection("sales").document(uid).collection("data")
                    .add(data)
                    .addOnSuccessListener(documentReference -> {
                        // Data uploaded successfully
                    })
                    .addOnFailureListener(e -> {
                        // Failed to upload data
                    });

            db.collection("Customers").document(uid).collection("data")
                    .add(data)
                    .addOnSuccessListener(documentReference -> {
                        // Data uploaded successfully
                    })
                    .addOnFailureListener(e -> {
                        // Failed to upload data
                    });
        }

    }

    public static String getTodayDate() {
        LocalDate today = null;
        String formattedDate = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formattedDate = today.format(formatter);
        }
        return formattedDate;
    }
    public void updateQuantity(String uid, String documentId, int newQuantity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("bill").document(uid).collection(getTodayDate()).document(documentId);

        // Update the "quantity" field with the new value
        docRef.update("quantity", newQuantity)
                .addOnSuccessListener(aVoid -> {
                    // Quantity updated successfully
                })
                .addOnFailureListener(e -> {
                    // Failed to update quantity
                });
    }

}

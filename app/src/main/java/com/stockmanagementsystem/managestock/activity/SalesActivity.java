package com.stockmanagementsystem.managestock.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.stockmanagementsystem.managestock.MainActivity;
import com.stockmanagementsystem.managestock.R;
import com.stockmanagementsystem.managestock.adapters.BillsAdapter;
import com.stockmanagementsystem.managestock.adapters.SalesAdapter;
import com.stockmanagementsystem.managestock.adapters.TodayBillAdapter;
import com.stockmanagementsystem.managestock.modelCLasses.GetBillsData;
import com.stockmanagementsystem.managestock.modelCLasses.SalesData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SalesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<GetBillsData> billsDataList = new ArrayList<>();
    private List<SalesData> salesDataList = new ArrayList<>();
    public BillsAdapter billsAdapter;
    public SalesAdapter salesAdapter;

    String uid = "";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();
         readSalesData();

        } else {

        }
    }

    //    private void readData() {
//
//        db.collection("bill").document(uid).collection("data").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                String name = document.getString("name");
//                                int age = document.getLong("age").intValue();
//                                String email = document.getString("email");
//                                String phone = document.getString("phone");
//                                String medicine = document.getString("medicine");
//                                String manufactureDate = document.getString("manufacture_date");
//                                String expireDate = document.getString("expire_date");
//                                int quantity = document.getLong("quantity").intValue();
//                                double price = document.getDouble("price");
//                                String doctorName = document.getString("doctor_name");
//                                String date = document.getString("date");
//
//
//
//
//                                GetBillsData m = new GetBillsData(name, age, email, phone,
//                                        medicine, manufactureDate, expireDate, quantity,
//                                        price, doctorName, date);
//                                billsDataList.add(m);
//                                Toast.makeText(SalesActivity.this, "read data", Toast.LENGTH_SHORT).show();
//                            }
//                            recyclerView = findViewById(R.id.recyclerViewMedicine);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(SalesActivity.this));
//                            billsAdapter = new TodayBillAdapter(getApplicationContext(), billsDataList);
//                            recyclerView.setAdapter(billsAdapter);
//                        } else {
//                            Toast.makeText(SalesActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//    }
//    public void readData() {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser != null) {
//            String uid = currentUser.getUid();
//
//            db.collection("sales").document(uid).collection("sales")
//                    .get()
//                    .addOnSuccessListener(queryDocumentSnapshots -> {
//
//
//                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                            String name = document.getString("name");
//                            int age = document.getLong("age") != null ? document.getLong("age").intValue() : 0;
//                            String email = document.getString("email");
//                            String phone = document.getString("phone");
//                            String medicine = document.getString("medicine");
//                            String manufactureDate = document.getString("manufacture_date");
//                            String expireDate = document.getString("expire_date");
//                            int quantity = document.getLong("quantity") != null ? document.getLong("quantity").intValue() : 0;
//                            double price = document.getDouble("price") != null ? document.getDouble("price") : 0.0;
//                            String doctorName = document.getString("doctor_name");
//                            String today = document.getString("today");
//
//                            GetBillsData bill = new GetBillsData(name, age, email, phone, medicine, manufactureDate, expireDate, quantity, price, doctorName, today);
//                            billsDataList.add(bill);
//                        }
//
//
//                        // Do something with the bills list here
//                    })
//                    .addOnFailureListener(e -> {
//                        // Failed to read data
//                    });
//        }
//        recyclerView = findViewById(R.id.recyclerViewMedicine2);
//        recyclerView.setLayoutManager(new LinearLayoutManager(SalesActivity.this));
//        billsAdapter = new BillsAdapter(getApplicationContext(), billsDataList);
//        recyclerView.setAdapter(billsAdapter);
//    }



    private void readSalesData() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();

        db.collection("sales").document(uid).collection("data").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String name = document.getString("name");
                                String  age = document.getString("age");
                                String email = document.getString("email");
                                String phone = document.getString("phone");
                                ArrayList<String> medicine = (ArrayList<String>) document.get("medicines");
                                String manufactureDate = document.getString("manufacture_date");
                                String expireDate = document.getString("expire_date");
                                String quantity = document.getString("quantity");
                                String  price = document.getString("price");
                                String doctorName = document.getString("doctor_name");
                                String today = document.getString("today");

                                // Create a SalesData object with the retrieved data
                                GetBillsData salesData = new GetBillsData(name, age, email, phone, medicine,
                                        manufactureDate, expireDate, quantity,
                                        price, doctorName, today);
                                billsDataList.add(salesData);
                            }
                            // Display the retrieved data in a RecyclerView
                            recyclerView = findViewById(R.id.recyclerViewMedicine2);
                            recyclerView.setLayoutManager(new LinearLayoutManager(SalesActivity.this));
                            salesAdapter = new SalesAdapter(getApplicationContext(), billsDataList);
                            recyclerView.setAdapter(salesAdapter);
                        } else {
                            Toast.makeText(SalesActivity.this, "Failed to read sales data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
package com.stockmanagementsystem.managestock.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.stockmanagementsystem.managestock.R;
import com.stockmanagementsystem.managestock.activity.AddMedicineActivity;
import com.stockmanagementsystem.managestock.activity.CustomersActivity;
import com.stockmanagementsystem.managestock.activity.SalesActivity;
import com.stockmanagementsystem.managestock.adapters.TodayBillAdapter;
import com.stockmanagementsystem.managestock.interfaces.ItemClickListener2;
import com.stockmanagementsystem.managestock.modelCLasses.GetBillsData;
import com.stockmanagementsystem.managestock.modelCLasses.UserPreferences;

import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    RecyclerView rvTodayBill;
    private List<GetBillsData> billsDataList = new ArrayList<>();
    public TodayBillAdapter billsAdapter;

    String uid = FirebaseAuth.getInstance().getUid();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView username, today_sale, amount, txt_empty;
    LinearLayout l1,l2,l3;
    int sale = 0;
    int price2 = 0;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvTodayBill = view.findViewById(R.id.rvTodayBill);
        username = view.findViewById(R.id.home_user_name);
        today_sale = view.findViewById(R.id.total_sale);
        amount = view.findViewById(R.id.total_amount);
        txt_empty = view.findViewById(R.id.txt_empty);
        l1 = view.findViewById(R.id.layoutAddMedi);
        l2 = view.findViewById(R.id.layoutSales);
        l3 = view.findViewById(R.id.layoutCustomers);
        onClick();
        readData();
        getUserData();
        return view;
    }


    public void onClick(){
l1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        requireActivity().finish();
        startActivity(new Intent(getContext(), AddMedicineActivity.class));
    }
});

l2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        requireActivity().finish();
        startActivity(new Intent(getContext(), SalesActivity.class));
    }
});

l3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        requireActivity().finish();
        startActivity(new Intent(getContext(), CustomersActivity.class));
    }
});

    }


    private void readData() {

        sale = 0;
        price2 = 0;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();


            String todayDate = getTodayDate();
            if (TextUtils.isEmpty(todayDate)) {
                Toast.makeText(getContext(), "Today's date is null or empty", Toast.LENGTH_SHORT).show();
                return;
            }

            db.collection("bill").document(uid).collection(todayDate).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<GetBillsData> billsDataList = new ArrayList<>();
                                for (DocumentSnapshot document : task.getResult()) {
                                    String name = document.getString("name");
                                    String age = document.getString("age");
                                    String email = document.getString("email");
                                    String phone = document.getString("phone");
                                    ArrayList<String> medicineList = (ArrayList<String>) document.get("medicines");
                                    String manufactureDate = document.getString("manufacture_date");
                                    String expireDate = document.getString("expire_date");
                                    String quantity = document.getString("quantity");
                                    String price = document.getString("price");
                                    String doctorName = document.getString("doctor_name");
                                    String date = document.getString("date");

                                    GetBillsData m = new GetBillsData(name, age, email, phone,
                                            medicineList, manufactureDate, expireDate, quantity,
                                            price, doctorName, date);
                                    billsDataList.add(m);


                                    if (!TextUtils.isEmpty(price)) {
                                        price2 += Integer.parseInt(price);
                                    }
                                }
                                if (billsDataList.size() == 0) {
                                    txt_empty.setVisibility(View.VISIBLE);
                                } else {
                                    txt_empty.setVisibility(View.GONE);
                                }
                                rvTodayBill.setLayoutManager(new LinearLayoutManager(getContext()));
                                billsAdapter = new TodayBillAdapter(getContext(), billsDataList);
                                rvTodayBill.setAdapter(billsAdapter);
                                amount.setText("Amount : " + price2 + ".Rs");
                                int totalSale = 0;
                                for (int i = 0; i < billsDataList.size(); i++) {
                                    totalSale = totalSale + billsDataList.get(i).getMedicineList().size();
                                }

                                today_sale.setText("Todays sale = " + totalSale+" items");
                            }

                        }
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

    private void getUserData() {
        UserPreferences userPreferences = new UserPreferences(getContext());
        String name = userPreferences.getUsername();
        String uid = userPreferences.getUid();
        String email = userPreferences.getEmail();

// Do something with the retrieved data
// For example, display it in a TextView
        String userInfo = "Name: " + name + "\nUID: " + uid + "\nEmail: " + email;
        username.setText("Hi " + name + ", ");
    }

}

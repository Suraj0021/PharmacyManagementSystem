package com.stockmanagementsystem.managestock.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stockmanagementsystem.managestock.R;
import com.stockmanagementsystem.managestock.adapters.MedicineAdapter;
import com.stockmanagementsystem.managestock.interfaces.ItemClickListener;
import com.stockmanagementsystem.managestock.modelCLasses.AddMedicineModelView;

import java.util.ArrayList;
import java.util.List;

public class ReportsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<AddMedicineModelView> getMedicineList = new ArrayList<>();
    String idd = "";
    public MedicineAdapter medicineAdapter;
    ItemClickListener itemClickListener;

    String uid = FirebaseAuth.getInstance().getUid();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public ReportsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewMedicine2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference uidRef = database.getReference("AllMedicineList").child(uid);

        uidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method will be called whenever the data at the database reference changes
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Loop through all child nodes of the uidRef
                    AddMedicineModelView medicine = snapshot.getValue(AddMedicineModelView.class);
                    // Get the AddMedicineModelView object from the snapshot
                    // You can now access the properties of the medicine object

                    Log.d("Medicine", medicine.toString());

                    getMedicineList.add(medicine);


                    String medicineName = medicine.getMedicine();
                    String company = medicine.getCompany();
                    String manufactureDate = medicine.getManufactureDate();
                    String expireDate = medicine.getExpireDate();
                    String quantity = medicine.getQuantity();
                    String price = medicine.getPrice();
                    // Do something with the medicine data
                }
                medicineAdapter = new MedicineAdapter(getActivity(), getMedicineList, itemClickListener);
                recyclerView.setAdapter(medicineAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur
            }
        });

        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(List<AddMedicineModelView> value) {
                // Display toast
                assert getFragmentManager() != null;
                FragmentTransaction f = getFragmentManager().beginTransaction();
                f.replace(R.id.fragment_container, new ReportsFragment());
                f.addToBackStack(null);
                f.commit();
            }
        };
        // set layout manager


        return view;
    }


}
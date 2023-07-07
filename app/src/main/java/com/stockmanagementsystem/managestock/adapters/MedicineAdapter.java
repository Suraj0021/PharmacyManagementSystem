package com.stockmanagementsystem.managestock.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stockmanagementsystem.managestock.MainActivity;
import com.stockmanagementsystem.managestock.R;
import com.stockmanagementsystem.managestock.activity.AddMedicineActivity;
import com.stockmanagementsystem.managestock.fragments.ReportsFragment;
import com.stockmanagementsystem.managestock.interfaces.ItemClickListener;
import com.stockmanagementsystem.managestock.modelCLasses.AddMedicineFireBase;
import com.stockmanagementsystem.managestock.modelCLasses.AddMedicineModelView;
import com.stockmanagementsystem.managestock.modelCLasses.GetMedicineData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    private final Context context;
    private final List<AddMedicineModelView> medicineList;
    String uid = FirebaseAuth.getInstance().getUid();
    ItemClickListener itemClickListener;
    public MedicineAdapter(Context context, List<AddMedicineModelView> medicineList, ItemClickListener itemClickListener) {
        this.context = context;
        this.medicineList = medicineList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_layout, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        AddMedicineModelView medicine = medicineList.get(position);


        if (holder.medicineTextView != null) {
            holder.medicineTextView.setText(medicine.getMedicine() + " * " + String.valueOf(medicine.getQuantity()));
            holder.manufactureDateTextView.setText("Manufacture Date : " + medicine.getManufactureDate());
            holder.expireDateTextView.setText("Expire Date : " + medicine.getExpireDate());
//            holder.quantityTextView.setText("Quantity : "+String.valueOf(medicine.getQuantity()));
            holder.priceTextView.setText(String.valueOf(medicine.getPrice()) + "Rs");
            holder.manufactureTextView.setText("Manufacturer : " + String.valueOf(medicine.getCompany()));


            holder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openEditDialog(medicine);
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteMedicineData(medicine.getMedicine());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        public final TextView medicineTextView;
        public final TextView manufactureDateTextView;

        public final TextView manufactureTextView;
        public final TextView expireDateTextView;
        //        public final TextView quantityTextView;
        public final TextView priceTextView;
        public ImageView update;
        public ImageView delete;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineTextView = itemView.findViewById(R.id.tv_medicine_name);
            manufactureDateTextView = itemView.findViewById(R.id.tv_manufacture_date);
            expireDateTextView = itemView.findViewById(R.id.tv_expire_date);
//            quantityTextView = itemView.findViewById(R.id.tv_quantity);
            priceTextView = itemView.findViewById(R.id.tv_price);
            manufactureTextView = itemView.findViewById(R.id.tv_manufacturer_company_name);
            update = itemView.findViewById(R.id.img_update);
            delete = itemView.findViewById(R.id.img_delete);

        }
    }

    private void openEditDialog(AddMedicineModelView medicine) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Inflate the edit dialog layout
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_medicine, null);

        // Find the EditText fields
        EditText priceEditText = view.findViewById(R.id.edit_text_price);
        EditText quantityEditText = view.findViewById(R.id.edit_text_quantity);
        EditText medicineName = view.findViewById(R.id.medicine_name);
        EditText companyName = view.findViewById(R.id.company_name);
        EditText manufacture_date = view.findViewById(R.id.manufacture_date);
        EditText expire_Date = view.findViewById(R.id.expire_date);

        // Set the values of the EditText fields
        priceEditText.setText(String.valueOf(medicine.getPrice()));
        quantityEditText.setText(String.valueOf(medicine.getQuantity()));

        builder.setView(view);
        builder.setPositiveButton("Save", (dialog, which) -> {
            // Get the updated values of the fields
            String price = priceEditText.getText().toString();
            String quantity = quantityEditText.getText().toString();

           updateData(medicine.getMedicine(), medicine.getCompany().toString(), medicine.getManufactureDate(), medicine.getExpireDate(), quantityEditText.getText().toString(),  priceEditText.getText().toString());
            Toast.makeText(context, "Medicine Added Successfully", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        });
        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    public void updateData( String medicine, String company, String manufactureDate, String expireDate, String quantity, String price){

        HashMap<String, Object> mediData = new HashMap<>();

        mediData.put("medicine",medicine);
        mediData.put("company",company);
        mediData.put("manufactureDate",manufactureDate);
        mediData.put("expireDate",expireDate);
        mediData.put("quantity",quantity);
        mediData.put("price",price);

        FirebaseDatabase.getInstance().getReference("AllMedicineList").child(uid).child(medicine).updateChildren(mediData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show();
            }
        });
        itemClickListener.onClick(medicineList);
    }



    public void deleteMedicineData(String medicine) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure") .setTitle("Delete");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to Delete this medicine data ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Toast.makeText(context,"Delete Successful",
                                Toast.LENGTH_SHORT).show();
                        FirebaseDatabase.getInstance().getReference("AllMedicineList").child(uid).child(medicine).removeValue();
                        itemClickListener.onClick(medicineList);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Delete Medicine");
        alert.show();

    }





}

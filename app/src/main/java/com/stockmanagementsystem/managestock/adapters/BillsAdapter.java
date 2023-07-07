package com.stockmanagementsystem.managestock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stockmanagementsystem.managestock.R;
import com.stockmanagementsystem.managestock.modelCLasses.GetBillsData;

import java.util.List;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillHolder> {


    private Context context;
    private final List<GetBillsData> billsDataList;



    public BillsAdapter(Context context, List<GetBillsData> billList) {
        this.context = context;
        this.billsDataList = billList;
    }


    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bills_layout, parent, false);
        return new BillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillHolder holder, int position) {


        GetBillsData m = billsDataList.get(position);
        holder.nameTextView.setText(m.getName());
        holder.ageTextView.setText(String.valueOf(m.getAge()));
        holder.emailTextView.setText(m.getEmail());
        holder.phoneTextView.setText(m.getPhone());
        if (m.getMedicineList() != null && m.getMedicineList().size() > 0) {
            holder.medicineTextView.setText("Medicine : "+m.getMedicineList().get(0));
        }
        holder.manufactureDateTextView.setText(m.getManufactureDate());
        holder.expireDateTextView.setText(m.getExpireDate());
        holder.quantityTextView.setText(String.valueOf(m.getQuantity()));
        holder.priceTextView.setText(String.valueOf(m.getPrice()));
        holder.doctorNameTextView.setText(m.getDoctorName());


    }


    @Override
    public int getItemCount() {

            return billsDataList.size();

    }

    public static class BillHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView ageTextView;
        public TextView emailTextView;
        public TextView phoneTextView;
        public TextView medicineTextView;
        public TextView manufactureDateTextView;
        public TextView expireDateTextView;
        public TextView quantityTextView;
        public TextView priceTextView;
        public TextView doctorNameTextView;

        public BillHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_textview);
            ageTextView = itemView.findViewById(R.id.age_textview);
            emailTextView = itemView.findViewById(R.id.email_textview);
            phoneTextView = itemView.findViewById(R.id.phone_textview);
            medicineTextView = itemView.findViewById(R.id.medicine_textview);
            manufactureDateTextView = itemView.findViewById(R.id.manufacture_date_textview);
            expireDateTextView = itemView.findViewById(R.id.expire_date_textview);
            quantityTextView = itemView.findViewById(R.id.quantity_textview);
            priceTextView = itemView.findViewById(R.id.price_textview);
            doctorNameTextView = itemView.findViewById(R.id.doctor_name_textview);
        }
    }
}
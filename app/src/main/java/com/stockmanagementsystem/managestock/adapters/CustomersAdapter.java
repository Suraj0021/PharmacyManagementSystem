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

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ViewHolder> {

    private Context context;
    private final List<GetBillsData> billsDataList;

    public CustomersAdapter(Context context, List<GetBillsData> billList) {
        this.context = context;
        this.billsDataList = billList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetBillsData m = billsDataList.get(position);
        holder.nameTextView.setText("Name : "+m.getName());
        holder.emailTextView.setText("Email : "+m.getEmail());
        holder.phoneTextView.setText("Phone : "+m.getPhone());

        String md = "";
        if (m.getMedicineList() != null && m.getMedicineList().size() > 0) {
            for (int i = 0; i <m.getMedicineList().size() ; i++) {
                md = md + m.getMedicineList().get(i)+", ";
            }
            holder.medicineTextView.setText(md);
        }


        holder.priceTextView.setText("Amount : "+String.valueOf(m.getPrice()) + "Rs");
    }

    @Override
    public int getItemCount() {
        return billsDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView emailTextView;
        public TextView phoneTextView;
        public TextView medicineTextView;
        public TextView quantityTextView;
        public TextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_textview);
            emailTextView = itemView.findViewById(R.id.email_textview);
            phoneTextView = itemView.findViewById(R.id.phone_textview);
            medicineTextView = itemView.findViewById(R.id.medicine_textview);
            quantityTextView = itemView.findViewById(R.id.quantity_textview);
            priceTextView = itemView.findViewById(R.id.price_textview);
        }
    }
}

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
import com.stockmanagementsystem.managestock.modelCLasses.GetMedicineData;
import com.stockmanagementsystem.managestock.modelCLasses.SalesData;

import java.util.List;
import java.util.Locale;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {

    private Context mContext;
    private List<GetBillsData> mSalesDataList;

    public SalesAdapter(Context context, List<GetBillsData> salesDataList) {
        mContext = context;
        mSalesDataList = salesDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sales, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetBillsData salesData = mSalesDataList.get(position);

        String md = "";
        if (salesData.getMedicineList() != null && salesData.getMedicineList().size() > 0) {
            for (int i = 0; i <salesData.getMedicineList().size() ; i++) {
                md = md + salesData.getMedicineList().get(i)+", ";
            }
            holder.medicineTextView.setText(md);
        }

//        holder.quantityTextView.setText("Quantity : "+salesData.getQuantity());
        holder.priceTextView.setText("Price : "+salesData.getPrice());
        holder.dateTextView.setText(salesData.getDate());
    }

    @Override
    public int getItemCount() {
        return mSalesDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView medicineTextView;
        public TextView quantityTextView;
        public TextView priceTextView;
        public TextView dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            medicineTextView = itemView.findViewById(R.id.medicine_name);
            quantityTextView = itemView.findViewById(R.id.quantity);
            priceTextView = itemView.findViewById(R.id.price);
            dateTextView = itemView.findViewById(R.id.date);
        }
    }
}

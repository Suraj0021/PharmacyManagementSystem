package com.stockmanagementsystem.managestock.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.stockmanagementsystem.managestock.R;
import com.stockmanagementsystem.managestock.interfaces.ItemClickListener2;
import com.stockmanagementsystem.managestock.modelCLasses.AddMedicineModelView;
import com.stockmanagementsystem.managestock.modelCLasses.MedicineAndQuantity;

import java.util.ArrayList;

public class MedicineQuantityAdapter extends RecyclerView.Adapter<MedicineQuantityAdapter.ViewHolder> {
    private ArrayList<AddMedicineModelView> mData;
    private ArrayList<Integer> listQnt = new ArrayList<>();
    ItemClickListener2 itemClickListener;

    String totalPrice = "";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView quantityTextView;
        public ImageView remove, addItem, removeItem;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.medicineNameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            remove = itemView.findViewById(R.id.remove);
            addItem = itemView.findViewById(R.id.plusOne);
            removeItem = itemView.findViewById(R.id.minusOne);

        }
    }

    public MedicineQuantityAdapter(ArrayList<AddMedicineModelView> data, ItemClickListener2 itemClickListener) {
        mData = data;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_quantity_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data item
        AddMedicineModelView medicine = mData.get(position);


        // set the views' values
        holder.nameTextView.setText(medicine.getMedicine());
        holder.quantityTextView.setText(String.valueOf("1"));
        listQnt.add(1);
        itemClickListener.onClick(position, "1", getTotalPrice(), selectedNames());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(position);
                notifyDataSetChanged();
                listQnt.remove(position);

            }
        });

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(holder.quantityTextView.getText().toString());
                if (a > 1) {
                    a = a - 1;
                    holder.quantityTextView.setText(String.valueOf(a));
                    listQnt.add(position, a);
                }

                itemClickListener.onClick(position, String.valueOf(a), getTotalPrice(), selectedNames());
            }
        });

        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(holder.quantityTextView.getText().toString());
                if (a < Integer.parseInt(medicine.getQuantity())) {
                    a = a + 1;
                    holder.quantityTextView.setText(String.valueOf(a));
                    listQnt.add(position, a);
                    totalPrice = String.valueOf(a * Integer.parseInt(mData.get(position).getPrice()));
                }
                itemClickListener.onClick(position, String.valueOf(a), getTotalPrice(), selectedNames());
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public String getTotalPrice() {
        totalPrice = "";
        int ans = 0;
        for (int i = 0; i < listQnt.size() && i < mData.size(); i++) {
            ans = ans + (listQnt.get(i) * Integer.parseInt(mData.get(i).getPrice()));
        }
        totalPrice = String.valueOf(ans);
        return totalPrice;
    }

    public ArrayList<String> selectedNames() {
        ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < mData.size() && i < listQnt.size(); i++) {
            names.add(mData.get(i).getMedicine() + " * " + listQnt.get(i));
        }
        return names;

    }
}

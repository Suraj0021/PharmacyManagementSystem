package com.stockmanagementsystem.managestock.interfaces;

import com.stockmanagementsystem.managestock.modelCLasses.AddMedicineModelView;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemClickListener {


    public abstract void onClick(List<AddMedicineModelView> value);
}

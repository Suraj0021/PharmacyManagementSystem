package com.stockmanagementsystem.managestock.interfaces;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class ItemClickListener2 {
    public abstract void onClick(int position, String quantity, String price, ArrayList<String> names);
}

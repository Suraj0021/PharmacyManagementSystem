package com.stockmanagementsystem.managestock.modelCLasses;

public class MedicineAndQuantity {
    private String name;
    private String d_id;
    private int quantity;

    public MedicineAndQuantity(String name, int quantity,String d_id) {
        this.name = name;
        this.quantity = quantity;
        this.d_id=d_id;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

package com.stockmanagementsystem.managestock.modelCLasses;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class AddMedicineModelView {
    private String medicine;
    private String company;
    private String manufactureDate;
    private String expireDate;
    private String  quantity;
    private String price;

    public AddMedicineModelView() {
        // Default constructor required for calls to DataSnapshot.getValue(Medicine.class)
    }

    public AddMedicineModelView(String medicine, String company, String manufactureDate, String expireDate, String quantity, String price) {

        this.medicine = medicine;
        this.company = company;
        this.manufactureDate = manufactureDate;
        this.expireDate = expireDate;
        this.quantity = quantity;
        this.price = price;
    }


    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String  getQuantity() {
        return quantity;
    }

    public void setQuantity(String  quantity) {
        this.quantity = quantity;
    }

    public String  getPrice() {
        return price;
    }

    public void setPrice(String  price) {
        this.price = price;
    }
}

package com.stockmanagementsystem.managestock.modelCLasses;

public class Medicine {
    private String medicine;
    private String company;
    private String manufactureDate;
    private String expireDate;
    private int quantity;
    private double price;

    public Medicine() {
        // Default constructor required for calls to DataSnapshot.getValue(Medicine.class)
    }

    public Medicine(String medicine, String company, String manufactureDate, String expireDate, int quantity, double price) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

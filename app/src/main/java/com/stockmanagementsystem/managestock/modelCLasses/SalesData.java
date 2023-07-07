package com.stockmanagementsystem.managestock.modelCLasses;

public class SalesData {
    private String medicine;
    private String manufactureDate;
    private String expireDate;
    private int quantity;
    private double price;
    private String doctorName;
    private String today;

    public SalesData(String medicine, String manufactureDate, String expireDate, int quantity, double price, String doctorName, String today) {
        this.medicine = medicine;
        this.manufactureDate = manufactureDate;
        this.expireDate = expireDate;
        this.quantity = quantity;
        this.price = price;
        this.doctorName = doctorName;
        this.today = today;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getToday() {
        return today;
    }
}

package com.stockmanagementsystem.managestock.modelCLasses;
import java.util.UUID;

public class GetMedicineData {
    private String id;
    private String medicine;
    private String company;
    private String manufactureDate;
    private String expireDate;
    private int quantity;
    private double price;

    public GetMedicineData(String id,String medicine, String company, String manufactureDate, String expireDate, int quantity, double price) {
        this.id = UUID.randomUUID().toString(); // generate a unique ID
        this.medicine = medicine;
        this.company = company;
        this.manufactureDate = manufactureDate;
        this.expireDate = expireDate;
        this.quantity = quantity;
        this.price = price;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

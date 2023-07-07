package com.stockmanagementsystem.managestock.modelCLasses;

import java.util.ArrayList;

public class GetBillsData {

    private String name;
    private String  age;
    private String email;
    private String phone;
    private ArrayList<String> medicineList;
    private String manufactureDate;
    private String expireDate;
    private String  quantity;
    private String  price;
    private String doctorName;
    private String date;

    public GetBillsData() {
        // Required empty public constructor
    }

    public GetBillsData(String name, String  age, String email, String phone, ArrayList<String> medicineList,
                        String manufactureDate, String expireDate, String  quantity, String  price,
                        String doctorName, String date) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.medicineList = medicineList;
        this.manufactureDate = manufactureDate;
        this.expireDate = expireDate;
        this.quantity = quantity;
        this.price = price;
        this.doctorName = doctorName;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<String> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(ArrayList<String> medicineList) {
        this.medicineList = medicineList;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName =doctorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

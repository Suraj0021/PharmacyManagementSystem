package com.stockmanagementsystem.managestock.modelCLasses;

public class UserModel {
    String email , name,address,pnumber,amount,item,pdat,Compname;

    public UserModel() {
    }


    public UserModel(String Compname, String email, String name, String address, String pnumber, String amount, String item, String pdat) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.pnumber = pnumber;
        this.amount = amount;
        this.item = item;
        this.pdat = pdat;
        this.Compname = Compname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPdat() {
        return pdat;
    }

    public void setPdat(String pdat) {
        this.pdat = pdat;
    }


    public String getCompname() {
        return Compname;
    }

    public void setCompname(String compname) {
        Compname = compname;
    }

}
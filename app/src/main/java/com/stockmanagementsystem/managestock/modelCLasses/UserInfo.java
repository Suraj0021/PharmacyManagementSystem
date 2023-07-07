package com.stockmanagementsystem.managestock.modelCLasses;


public class UserInfo {
    String address;
    String email;
    String name;
    Long pNo;
    String DOB;


    public UserInfo(String address, String email, String name, Long pNo) {
        this.address = address;
        this.email = email;
        this.name = name;
        this.pNo = pNo;

    }
    public UserInfo() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getpNo() {
        return pNo;
    }

    public void setpNo(Long pNo) {
        this.pNo = pNo;
    }


}
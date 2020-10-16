package com.example.couchpotatolk.Model;

public class Vendors {

    private String name, phone, email, password, website, description;

    public Vendors(){

    }

    public Vendors(String name, String phone, String email, String password, String website, String description) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.website = website;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

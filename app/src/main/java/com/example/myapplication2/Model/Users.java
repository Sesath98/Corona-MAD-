package com.example.myapplication2.Model;

public class Users
{
    private String name, password,phone;

    public Users()
    {

    }

    public Users(String name, String phone , String password)
    {
        this.name=name;
        this.password=password;
        this.phone=phone;

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPhone()

    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
}



package com.beerconnect.assignment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClientRecord {
    @Id
    private int id;
    private String name;
    private String email;
    private String address;
    private String preferences;

    // default constructor
    public ClientRecord() {

    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPreferences()
    {
        return preferences;
    }
    public void setPreferences(String preferences)
    {
        this.preferences = preferences;
    }
}



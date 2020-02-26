package com.example.fyp_reminder;

public class Contacts
{
    public String email, uid;

    public  Contacts()
    {

    }

    public Contacts(String email, String uid)
    {
        this.email = email;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 *
 */
public class Employee {

    private int id;

    private String email;

    private String dob;

    private String gender;

    private String username;

    public Employee(int id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getDob ()
    {
        return dob;
    }

    public void setDob (String dob)
    {
        this.dob = dob;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }





    public String getFirstname ()
    {
        return username;
    }

    public void setFirstname (String firstname)
    {
        this.username
                = firstname;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", email = "+email+", dob = "+dob+", gender = "+gender+", username = "+username+"]";
    }
}
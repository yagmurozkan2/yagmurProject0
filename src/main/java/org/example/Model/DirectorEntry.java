package org.example.Model;

import java.util.*;

public class DirectorEntry {

    private int ID;
    private String FirstName;
    private String LastName;

    public DirectorEntry(int ID, String FirstName, String LastName){
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
    }

    public int getID() { return this.ID; }

    public String getFirstName() { return this.FirstName; }

    public String getLastName() { return this.LastName; }

    public void setFirstName(String FirstName) { this.FirstName = FirstName; }

    public void setLastName(String LastName) { this.LastName = LastName; }


    public String toString(){
        return "Director ID: " + this.ID + " - Name: " + this.FirstName + " " + this.LastName;
    }

}

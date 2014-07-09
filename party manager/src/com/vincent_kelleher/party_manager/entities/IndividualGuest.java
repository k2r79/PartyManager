package com.vincent_kelleher.party_manager.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "individual_guest")
public class IndividualGuest extends Guest
{
    @DatabaseField
    private String firstname;

    @DatabaseField
    private String lastname;

    public IndividualGuest(String firstname, String lastname)
    {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    @Override
    public String toString()
    {
        return firstname + " " + lastname;
    }
}

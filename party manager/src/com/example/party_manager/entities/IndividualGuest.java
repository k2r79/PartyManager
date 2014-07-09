package com.example.party_manager.entities;

public class IndividualGuest extends Guest
{
    private String firstname;
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

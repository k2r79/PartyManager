package com.example.party_manager.entities;

import java.util.List;

public class CoupleGuest extends Guest
{
    private List<Guest> guests;

    public CoupleGuest(List<Guest> guests)
    {
        this.guests = guests;
    }

    public List<Guest> getGuests()
    {
        return guests;
    }

    public void setGuests(List<Guest> guests)
    {
        this.guests = guests;
    }

    @Override
    public String toString()
    {
        String name = "";

        for (Guest guest : guests) {
            name += guest.toString() + " ";
        }

        return name;
    }
}

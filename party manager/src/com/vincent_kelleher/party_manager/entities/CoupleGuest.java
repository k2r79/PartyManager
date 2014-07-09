package com.vincent_kelleher.party_manager.entities;

import com.j256.ormlite.field.DatabaseField;

import java.util.List;

public class CoupleGuest extends Guest
{
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
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

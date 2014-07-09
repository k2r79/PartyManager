package com.vincent_kelleher.party_manager.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "couple_guest")
public class CoupleGuest extends Guest
{
    @ForeignCollectionField(eager = true)
    private ForeignCollection<Guest> guests;

    public CoupleGuest()
    {
    }

    public CoupleGuest(ForeignCollection<Guest> guests)
    {
        this.guests = guests;
    }

    public ForeignCollection<Guest> getGuests()
    {
        return guests;
    }

    public void setGuests(ForeignCollection<Guest> guests)
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

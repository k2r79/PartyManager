package com.vincent_kelleher.party_manager.entities;

import com.j256.ormlite.field.DatabaseField;

public abstract class Guest
{
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = true, foreign = true, foreignAutoCreate = true,foreignAutoRefresh = true)
    protected Room room;

    @DatabaseField
    protected String phone;

    @DatabaseField
    protected boolean present;

    @DatabaseField(foreign = true)
    protected CoupleGuest couple;

    protected Guest()
    {
    }

    public Room getRoom()
    {
        return room;
    }

    public void setRoom(Room room)
    {
        this.room = room;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public boolean isPresent()
    {
        return present;
    }

    public void setPresent(boolean present)
    {
        this.present = present;
    }

    public CoupleGuest getCouple()
    {
        return couple;
    }

    public void setCouple(CoupleGuest couple)
    {
        this.couple = couple;
    }

    @Override
    public String toString()
    {
        return null;
    }
}

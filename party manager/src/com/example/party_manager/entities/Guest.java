package com.example.party_manager.entities;

public abstract class Guest
{
    protected Room room;
    protected String phone;
    protected boolean present;

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

    @Override
    public String toString()
    {
        return null;
    }
}

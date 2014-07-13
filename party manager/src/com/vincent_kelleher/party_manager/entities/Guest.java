package com.vincent_kelleher.party_manager.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "guest")
public class Guest
{
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String imagePath;

    @DatabaseField
    private String name;

    @DatabaseField(canBeNull = true, foreign = true, foreignAutoCreate = true,foreignAutoRefresh = true)
    private Room room;

    @DatabaseField
    private String phone;

    @DatabaseField
    private int headcount;

    @DatabaseField
    private boolean present;

    private Guest()
    {
    }

    public Guest(String name)
    {
        this.name = name;
        this.headcount = 1;
    }

    public Guest(String name, int headcount)
    {
        this(name);
        this.headcount = headcount;
    }

    public Guest(String name, int headcount, Room room)
    {
        this(name, headcount);
        this.room = room;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public int getHeadcount()
    {
        return headcount;
    }

    public void setHeadcount(int headcount)
    {
        this.headcount = headcount;
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
        return name;
    }
}

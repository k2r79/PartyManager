package com.vincent_kelleher.party_manager.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "room")
public class Room
{
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private int positionX;

    @DatabaseField
    private int positionY;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPositionX()
    {
        return positionX;
    }

    public void setPositionX(int positionX)
    {
        this.positionX = positionX;
    }

    public int getPositionY()
    {
        return positionY;
    }

    public void setPositionY(int positionY)
    {
        this.positionY = positionY;
    }
}
